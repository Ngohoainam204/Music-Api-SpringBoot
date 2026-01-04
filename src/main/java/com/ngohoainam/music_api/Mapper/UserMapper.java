package com.ngohoainam.music_api.Mapper;

import com.ngohoainam.music_api.dto.request.userRequest.UserCreateRequest;
import com.ngohoainam.music_api.dto.request.userRequest.UserUpdateRequest;
import com.ngohoainam.music_api.dto.response.UserResponse;
import com.ngohoainam.music_api.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
@Component
@Mapper(componentModel = "spring")

public interface UserMapper {
    User toUser(UserCreateRequest request);

    User toUser(UserUpdateRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
