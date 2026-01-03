package com.ngohoainam.music_api.dto.request.songRequest;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
public class SongCreateRequest {
    private String title;
    private int durationSeconds;
    private boolean explicit = false;
    private boolean isPublished;
    private int priceCents ;
    private String description;
    private String sku;
    private Long albumId;
    private Long artistId;

}

