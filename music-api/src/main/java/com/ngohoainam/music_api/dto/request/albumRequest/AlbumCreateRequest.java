package com.ngohoainam.music_api.dto.request.albumRequest;

import com.ngohoainam.music_api.entity.Song;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AlbumCreateRequest {
    String title;
    String url;
    String description;
    Long artistId;
    Set<Song> songIds;
}
