package com.ngohoainam.music_api.Mapper;

import com.ngohoainam.music_api.dto.request.artistRequest.ArtistCreateRequest;
import com.ngohoainam.music_api.dto.request.artistRequest.ArtistUpdateRequest;
import com.ngohoainam.music_api.dto.response.ArtistResponse;
import com.ngohoainam.music_api.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {AlbumMapper.class,SongMapper.class})
public interface ArtistMapper {
    Artist toArtist(ArtistCreateRequest request);
    Artist toArtist(ArtistUpdateRequest request);

    @Mapping(source = "id", target = "id")
    @Mapping(target = "songTitles" , expression =
            "java(artist.getSongs() != null ? artist.getSongs().stream().map(song->song.getTitle()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "albumTitles" , expression =
            "java(artist.getAlbums() != null ? artist.getAlbums().stream().map(album->album.getTitle()).collect(java.util.stream.Collectors.toList()) : null)")
    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "songs",ignore = true)
    ArtistResponse toSummaryResponse(Artist artist);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "albums", target = "albums")
    @Mapping(source = "songs", target = "songs")
    @Mapping(source = "createdAt",target = "createdAt")
    @Mapping(target = "songTitles",ignore = true)
    @Mapping(target = "albumTitles",ignore = true)
    ArtistResponse toDetailResponse(Artist artist);
    
    @Mapping(target = "name", source = "name")
    @Mapping(target = "bio", source = "bio")
    @Mapping(target = "url", source = "url")
    void updateArtist(@MappingTarget Artist artist, ArtistUpdateRequest request);
}
