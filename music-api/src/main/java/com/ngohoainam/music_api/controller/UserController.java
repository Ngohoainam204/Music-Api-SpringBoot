package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.dto.request.userRequest.UserCreateRequest;
import com.ngohoainam.music_api.dto.response.ApiResponse;
import com.ngohoainam.music_api.dto.response.UserResponse;
import com.ngohoainam.music_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request){

        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Create Successful")
                .result(userService.createUser(request))
                .build();
    }
    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") Long id){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.getUserById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<UserResponse> deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Delete Successful")
                .build();
    }
}
