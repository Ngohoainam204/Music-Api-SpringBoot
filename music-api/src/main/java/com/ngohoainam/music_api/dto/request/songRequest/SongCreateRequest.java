package com.ngohoainam.music_api.dto.request.songRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongCreateRequest {
    String title;
    int durationSeconds;
    boolean explicit;
    int priceCents;
    boolean isPublished;
    String sku;
    Long albumId;
    Long artistId;

}

