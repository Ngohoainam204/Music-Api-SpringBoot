package com.ngohoainam.music_api.dto.request.songRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongCreateRequest {
    String title;
    String url;
    int duration;
    String lyric;
    boolean isPrivate = false;
    Long albumId;
    Long artistId;

}

