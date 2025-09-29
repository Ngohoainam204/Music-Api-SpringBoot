package com.ngohoainam.music_api.dto.request.userRequest;

import com.ngohoainam.music_api.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
     String email;
     boolean emailVerified;
     String password;
     String displayName;
     LocalDateTime createdAt;
     LocalDateTime updatedAt;
     String status;
     Set<Role> roles;
}
