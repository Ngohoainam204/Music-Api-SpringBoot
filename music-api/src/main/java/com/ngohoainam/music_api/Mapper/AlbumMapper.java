package com.ngohoainam.music_api.Mapper;

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
    Album toAlbum(AlbumCreateRequest request);
    @Mapping(source = "id", target = "id")
    @Mapping(target = "artistName", expression = "java(album.getArtist() != null ? album.getArtist().getName() : null)")
    @Mapping(target = "songTitles",expression = "java(album.getSongs() != null ? album.getSongs().stream().map(song->song.getTitle()).collect(java.util.stream.Collectors.toList()) : null)")
    AlbumResponse toResponse(Album album);

    void updateAlbum(@MappingTarget Album album, AlbumCreateRequest request);
}
