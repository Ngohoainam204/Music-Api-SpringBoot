package com.ngohoainam.music_api.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongResponse {
    Long id;
    String title;
    String url;
    int duration;
    String lyric;
    String albumTitle;
    String artistName;
    boolean isPrivate;
}
