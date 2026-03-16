package com.ngohoainam.music_api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistResponse {
    Long id;
    String name;
    String url;
    String bio;
    String profileImage;
    Instant createdAt;
    //
    List<String> songTitles;
    List<String> albumTitles;
    //
    List<AlbumResponse> albums;
    List<SongResponse> songs;
}


