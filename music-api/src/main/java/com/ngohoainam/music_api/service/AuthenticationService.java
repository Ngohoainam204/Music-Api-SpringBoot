package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.dto.request.AuthenticationRequest;
import com.ngohoainam.music_api.dto.response.AuthenticationResponse;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.enums.Permissions;
import com.ngohoainam.music_api.enums.Roles;
import com.ngohoainam.music_api.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Data
public class AuthenticationService {
    private final UserRepository userRepository;

    @NonFinal
    @Value( "${spring.jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticated(AuthenticationRequest request) throws KeyLengthException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());

        if(!authenticated)
            throw new RuntimeException("Authentication failed");

        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .authenticated(true)
                .build();
    }

    public String generateToken(User user) throws KeyLengthException {
        Roles roles = Roles.valueOf(user.getRoles().toString());
        Set<Permissions> permissions = roles.getPermissions();
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("namsalt8@gmail.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("roles",List.of(roles.name()))
                .claim("permissions",permissions.stream().map(Permissions::name).toList())
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
