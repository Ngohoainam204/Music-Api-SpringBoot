package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.dto.request.AuthenticationRequest;
import com.ngohoainam.music_api.dto.request.RefreshTokenRequest;
import com.ngohoainam.music_api.dto.response.AuthenticationResponse;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.entity.UserDevice;
import com.ngohoainam.music_api.enums.Roles;
import com.ngohoainam.music_api.exception.AppException;
import com.ngohoainam.music_api.exception.ErrorCode;
import com.ngohoainam.music_api.repository.UserDeviceRepository;
import com.ngohoainam.music_api.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${spring.jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${spring.jwt.refresh-token.expiration:7}")
    protected long REFRESH_TOKEN_EXPIRATION;

    @Transactional
    public AuthenticationResponse authenticated(AuthenticationRequest request) throws KeyLengthException {
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());

        if (!authenticated)
            throw new AppException(ErrorCode.INVALID_PASSWORD);

        // DRM - Device limit logic
        handleDeviceLimit(user, request.getDeviceId());

        var accessToken = generateToken(user);
        var refreshToken = generateRefreshToken(user);
        userRepository.save(user); // Save the updated user with new refresh token

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

    private void handleDeviceLimit(User user, String deviceId) {
        String normalizedDeviceId = normalizeDeviceId(deviceId);
        if (normalizedDeviceId == null) {
            // If deviceId is not provided, skip the DRM check
            return;
        }
        long deviceCount = userDeviceRepository.countByUserId(user.getId());
        Optional<UserDevice> existingDevice = userDeviceRepository.findByUserIdAndDeviceId(user.getId(), normalizedDeviceId);

        if (existingDevice.isPresent()) {
            // Device already registered, update last login time
            UserDevice device = existingDevice.get();
            device.setLastLogin(Instant.now());
            userDeviceRepository.save(device);
        } else {
            // New device
            if (deviceCount >= 2) {
                // Some web clients rotate deviceId too often.
                // If there is a very recent login, rebind that slot instead of blocking same-machine re-login.
                List<UserDevice> devices = userDeviceRepository.findByUserIdOrderByLastLoginDesc(user.getId());
                if (!devices.isEmpty()) {
                    UserDevice mostRecentDevice = devices.get(0);
                    Instant threshold = Instant.now().minus(12, ChronoUnit.HOURS);
                    if (mostRecentDevice.getLastLogin() != null && mostRecentDevice.getLastLogin().isAfter(threshold)) {
                        mostRecentDevice.setDeviceId(normalizedDeviceId);
                        mostRecentDevice.setLastLogin(Instant.now());
                        userDeviceRepository.save(mostRecentDevice);
                        return;
                    }
                }
                throw new AppException(ErrorCode.DEVICE_LIMIT_EXCEEDED);
            }
            UserDevice newDevice = UserDevice.builder()
                    .user(user)
                    .deviceId(normalizedDeviceId)
                    .lastLogin(Instant.now())
                    .build();
            userDeviceRepository.save(newDevice);
        }
    }

    private String normalizeDeviceId(String deviceId) {
        if (deviceId == null) return null;
        String normalized = deviceId.trim();
        return normalized.isEmpty() ? null : normalized;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws KeyLengthException {
        User user = userRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

        if (user.getRefreshTokenExpiry() != null && user.getRefreshTokenExpiry().isBefore(Instant.now())) {
            throw new AppException(ErrorCode.UNAUTHORIZED); // Refresh token expired
        }

        var accessToken = generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

    private String generateRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        user.setRefreshToken(token);
        user.setRefreshTokenExpiry(Instant.now().plus(REFRESH_TOKEN_EXPIRATION, ChronoUnit.DAYS));
        return token;
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUserByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private String generateToken(User user) throws KeyLengthException {
        Roles roleEnum = user.getRoles() != null ? user.getRoles() : Roles.USER;
        
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("namsalt8@gmail.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("userId", user.getId())
                .claim("roles", List.of(roleEnum.name()))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes(StandardCharsets.UTF_8)));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error when generate token", e);
            throw new RuntimeException(e);
        }
    }
}


