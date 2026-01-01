package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.dto.ApiResponse;
import com.ngohoainam.music_api.dto.request.AuthenticationRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true) // Tự động thêm final cho các field
public class AuthenticationController {

    AuthenticationService authenticationService;
    UserService userService;
    // Đã xóa userRepository và userMapper vì không dùng

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws KeyLengthException {
        // Service xử lý logic -> trả về kết quả -> Controller bọc lại bằng success
        var result = authenticationService.authenticated(request);

        return ApiResponse.success(result);
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody @Valid UserCreateRequest request) {
        // Hàm registerUser trong Service nên trả về UserResponse luôn
        var result = userService.registerUser(request);

        return ApiResponse.created(result);
    }
}