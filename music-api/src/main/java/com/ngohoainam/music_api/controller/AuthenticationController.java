package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.dto.request.AuthenticationRequest;
import com.ngohoainam.music_api.dto.response.ApiResponse;
import com.ngohoainam.music_api.dto.response.AuthenticationResponse;
import com.ngohoainam.music_api.service.AuthenticationService;
import com.nimbusds.jose.KeyLengthException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    ResponseEntity<ApiResponse<AuthenticationResponse>> authenticateUser(@RequestBody AuthenticationRequest request) throws KeyLengthException {
        AuthenticationResponse authenticationResponse = authenticationService.authenticated(request);

        ApiResponse<AuthenticationResponse> apiResponse = ApiResponse.<AuthenticationResponse>builder()
                 .code(200)
                 .message("Login successful")
                 .result(authenticationResponse)
                 .build();
         return ResponseEntity.ok(apiResponse);
    }

}
