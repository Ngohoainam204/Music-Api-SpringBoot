package com.ngohoainam.music_api.dto.request.userRequest;

import com.ngohoainam.music_api.entity.Role;
import com.ngohoainam.music_api.enums.Roles;
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
     String password;
     String displayName;
     String status = "ACTIVE";
}
