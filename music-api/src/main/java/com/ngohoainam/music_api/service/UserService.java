package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.Mapper.UserMapper;
import com.ngohoainam.music_api.dto.request.userRequest.UserCreateRequest;
import com.ngohoainam.music_api.dto.response.UserResponse;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.enums.Roles;
import com.ngohoainam.music_api.exception.AppException;
import com.ngohoainam.music_api.exception.ErrorCode;
import com.ngohoainam.music_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserCreateRequest request){
        if(userRepository.existsUserByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        user.setRoles(Roles.USER);
        ;
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public List<UserResponse> getAllUsers(){
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
    public UserResponse getUserById(Long id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found")));
    }
    public void deleteUserById(Long id){
        userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }
    public void updateUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException(ErrorCode.USER_NOT_FOUND.getMessage()));
        user.setRoles(Roles.ARTIST);
        userRepository.save(user);
    }
    public UserResponse getMyInfo(Long id){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        User user = userRepository.findUserByEmail(email).orElseThrow(()->new RuntimeException(ErrorCode.USER_NOT_FOUND.getMessage()));
        return userMapper.toUserResponse(user);
    }
}
