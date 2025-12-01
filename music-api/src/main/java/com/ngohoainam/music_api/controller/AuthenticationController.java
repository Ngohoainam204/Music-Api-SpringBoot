package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.Mapper.UserMapper;
import com.ngohoainam.music_api.dto.request.AuthenticationRequest;
import com.ngohoainam.music_api.dto.request.userRequest.UserCreateRequest;
import com.ngohoainam.music_api.dto.response.ApiResponse;
import com.ngohoainam.music_api.dto.response.AuthenticationResponse;
import com.ngohoainam.music_api.dto.response.UserResponse;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.repository.UserRepository;
import com.ngohoainam.music_api.service.AuthenticationService;
import com.ngohoainam.music_api.service.UserService;
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

    private final UserRepository userRepository;

    private final UserService userService;

    private final UserMapper userMapper;
    @PostMapping("/login")

    ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody AuthenticationRequest request) throws KeyLengthException {
        AuthenticationResponse authenticationResponse = authenticationService.authenticated(request);

        ApiResponse<AuthenticationResponse> apiResponse = ApiResponse.<AuthenticationResponse>builder()
                 .code(200)
                 .message("Login successful")
                 .result(authenticationResponse)
                 .build();
         return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/register")
    ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody UserCreateRequest request) throws KeyLengthException {
            ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                    .code(200)
                    .message("Register successful")
                    .result(userService.registerUser(request))
                    .build();
            return ResponseEntity.ok(apiResponse);
        }
    }

