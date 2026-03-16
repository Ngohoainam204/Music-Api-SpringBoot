package com.ngohoainam.music_api.dto.request.userRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

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


