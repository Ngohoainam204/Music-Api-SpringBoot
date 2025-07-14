package com.ngohoainam.music_api.dto.response;

import com.fasterxml.jackson.databind.node.LongNode;
import com.ngohoainam.music_api.entity.Song;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
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
