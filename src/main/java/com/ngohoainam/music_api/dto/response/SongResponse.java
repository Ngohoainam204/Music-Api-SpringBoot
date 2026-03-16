package com.ngohoainam.music_api.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SongResponse {
    Long id;
    String title;
    int durationSeconds;
    boolean explicit;
    int priceCents;
    boolean isPublished;
    String sku;
    Instant createdAt;
    Long albumId;
    Long artistId;
}

