package com.ngohoainam.music_api.dto.request.artistRequest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ArtistCreateRequest {
    String name;
    String profileImage;
    String url;
    String bio;
}
