package com.ngohoainam.music_api.dto.request.albumRequest;

import com.ngohoainam.music_api.entity.Song;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AlbumUpdateRequest {
    String title;
    LocalDateTime releaseDate;
    String coverImage;
    LocalDateTime createAt;
    Long artistId;
    Set<Song> songIds;
}
