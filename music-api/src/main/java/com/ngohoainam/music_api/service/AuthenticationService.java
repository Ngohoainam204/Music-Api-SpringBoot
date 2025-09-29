package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.dto.request.AuthenticationRequest;
import com.ngohoainam.music_api.dto.response.ApiResponse;
import com.ngohoainam.music_api.dto.response.AuthenticationResponse;
import com.ngohoainam.music_api.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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

        var token = generateToken(request.getEmail());

        new AuthenticationResponse();

        return AuthenticationResponse.builder()
                .accessToken(token)
                .authenticated(true)
                .build();
    }

    public String generateToken(String username) throws KeyLengthException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("namsalt8.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("role", "USER")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error when generate token", e);
            throw new RuntimeException(e);
        }
    }
}
