package com.ngohoainam.music_api.mapper;

import com.ngohoainam.music_api.dto.request.userRequest.UserCreateRequest;
import com.ngohoainam.music_api.dto.response.UserResponse;
import com.ngohoainam.music_api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "passwordHash", ignore = true) // Password will be encoded in service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true) // Roles will be set in service
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "refreshTokenExpiry", ignore = true)
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);
}


