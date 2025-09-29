package com.ngohoainam.music_api.dto.response;

import com.ngohoainam.music_api.entity.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String email;
    Boolean emailVerified;
    String passwordHash;
    String displayName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String status;
    Set<Role> roles;
}
