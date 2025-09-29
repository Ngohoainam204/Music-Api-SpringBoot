package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.Mapper.UserMapper;
import com.ngohoainam.music_api.dto.request.userRequest.UserCreateRequest;
import com.ngohoainam.music_api.dto.response.UserResponse;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse createUser(UserCreateRequest request){
        if(userRepository.existsUserByEmail(request.getEmail()))
            throw new RuntimeException("Username already exists");
        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
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

}
