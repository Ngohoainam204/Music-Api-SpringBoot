package com.ngohoainam.music_api.dto.request.albumRequest;

import com.ngohoainam.music_api.entity.Song;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AlbumCreateRequest {
    String title;
    Instant releaseDate;
    String coverImage;
    Instant createAt;
    Long artistId;
    Set<Song> songIds;
}

