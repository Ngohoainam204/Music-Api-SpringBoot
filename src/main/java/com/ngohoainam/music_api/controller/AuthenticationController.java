package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.dto.ApiResponse;
import com.ngohoainam.music_api.dto.request.AuthenticationRequest;
import com.ngohoainam.music_api.dto.request.RefreshTokenRequest;
import com.ngohoainam.music_api.dto.request.userRequest.UserCreateRequest;
import com.ngohoainam.music_api.dto.response.AuthenticationResponse;
import com.ngohoainam.music_api.dto.response.UserResponse;
import com.ngohoainam.music_api.service.AuthenticationService;
import com.ngohoainam.music_api.service.UserService;
import com.nimbusds.jose.KeyLengthException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;
    UserService userService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws KeyLengthException {
        var result = authenticationService.authenticated(request);
        return ApiResponse.success(result);
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws KeyLengthException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.success(result);
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody @Valid UserCreateRequest request) {
        var result = userService.registerUser(request);
        return ApiResponse.created(result);
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.success(userService.getMyInfo());
    }
}


