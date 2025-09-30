package com.ngohoainam.music_api.dto.request.userRequest;

import com.ngohoainam.music_api.entity.Role;
import com.ngohoainam.music_api.enums.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String email;
    boolean emailVerified;
    String passwordHash;
    String displayName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String status;
    Roles roles;
}
