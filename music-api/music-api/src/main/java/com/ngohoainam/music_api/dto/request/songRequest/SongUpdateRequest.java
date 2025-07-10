package com.ngohoainam.music_api.dto.request.songRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongUpdateRequest {
    String title;
    String url;
    int duration;
    String lyric;
    boolean isPrivate = false;
    Long album_id;
    Long artist_id;

}
