package com.ngohoainam.music_api.Mapper;


import com.ngohoainam.music_api.dto.request.songRequest.SongCreateRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongUpdateRequest;
import com.ngohoainam.music_api.dto.response.SongResponse;
import com.ngohoainam.music_api.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface SongMapper {
    Song toSong(SongCreateRequest request);

    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "durationSeconds", target = "durationSeconds")
    @Mapping(source = "createdAt", target = "createdAt")
    SongResponse toSongResponse(Song song);

    void updateSong(@MappingTarget Song song, SongUpdateRequest request);
}
