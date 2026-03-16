package com.ngohoainam.music_api.mapper;

import com.ngohoainam.music_api.dto.request.albumRequest.AlbumCreateRequest;
import com.ngohoainam.music_api.dto.response.AlbumResponse;
import com.ngohoainam.music_api.entity.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AlbumMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Album toAlbum(AlbumCreateRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(target = "artistName", expression = "java(album.getArtist() != null ? album.getArtist().getName() : null)")
    AlbumResponse toResponse(Album album);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateAlbum(@MappingTarget Album album, AlbumCreateRequest request);
}


