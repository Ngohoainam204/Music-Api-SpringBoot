package com.ngohoainam.music_api.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlbumResponse {
     Long id;
     String title;
     String url;
     String description;
     String artistName;
     List<String> songTitles;
}


