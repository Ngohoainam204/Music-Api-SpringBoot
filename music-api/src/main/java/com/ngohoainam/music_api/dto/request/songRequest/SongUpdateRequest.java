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
    int durationSeconds;
    boolean explicit;
    int priceCents;
    boolean isPublished;
    String sku;
    Long albumId;
    Long artistId;

}
