package com.ngohoainam.music_api.mapper;


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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Song toSong(SongCreateRequest request);

    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "durationSeconds", target = "durationSeconds")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(target = "albumId", source = "album.id")
    SongResponse toSongResponse(Song song);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "description", ignore = true)
    void updateSong(@MappingTarget Song song, SongUpdateRequest request);
}


