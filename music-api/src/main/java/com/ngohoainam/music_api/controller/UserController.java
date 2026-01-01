package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.dto.request.userRequest.UserCreateRequest;
import com.ngohoainam.music_api.dto.ApiResponse;
import com.ngohoainam.music_api.dto.response.UserResponse;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.enums.Roles;
import com.ngohoainam.music_api.exception.ErrorCode;
import com.ngohoainam.music_api.repository.UserRepository;
import com.ngohoainam.music_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request){

        return ApiResponse.created(userService.registerUser(request));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(){
        return ApiResponse.success(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") Long id){
        return ApiResponse.success(userService.getUserById(id));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return ApiResponse.success(ErrorCode.DELETE_SUCCESS.getMessage());
    }
    @PostMapping("{id}/upgrade-to-artist")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> upgradeUserToArtist(@PathVariable("id") Long id){
        userService.updateUserById(id);
        return ApiResponse.success(ErrorCode.SUCCESS.getMessage());
    }


}
