package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.mapper.AlbumMapper;
import com.ngohoainam.music_api.dto.request.albumRequest.AlbumCreateRequest;
import com.ngohoainam.music_api.dto.response.AlbumResponse;
import com.ngohoainam.music_api.entity.Album;
import com.ngohoainam.music_api.entity.Artist;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.repository.AlbumRepository;
import com.ngohoainam.music_api.repository.ArtistRepository;
import com.ngohoainam.music_api.repository.SongRepository;
import com.ngohoainam.music_api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumService  {

    private final AlbumRepository albumRepository;

    private final ArtistRepository artistRepository;

    private final SongRepository songRepository;

    private final AlbumMapper albumMapper;

    private final AuthenticationService authenticationService;


    public AlbumResponse createAlbum(AlbumCreateRequest request){
        User user = authenticationService.getCurrentUser();
        Artist artist = artistRepository.findByUser(user).orElse(null);
        log.info("displayname" + user.getDisplayName());
        if(artist == null) {
            // Chưa có thì tạo mới ngay lập tức
            artist = new Artist();
            artist.setUser(user);
            artist.setName(user.getDisplayName());
            artist.setBio("New artist");
            artist.setUser(user);
            log.info("displayname" + artist.getName());
            artist = artistRepository.save(artist);
        }
        Album newAlbum = new Album();
        newAlbum.setTitle(request.getTitle());
        newAlbum.setReleaseDate(request.getReleaseDate());
        newAlbum.setCoverImage(request.getCoverImage());
        newAlbum.setArtist(artist);

        // The logic to add songs to an album has been removed as the relationship was deleted.
        // We can re-implement this later if needed.

        return albumMapper.toResponse(albumRepository.save(newAlbum));
        }
        public List<AlbumResponse> getAllAlbums(){
        return albumRepository.findAll().stream()
                .map(albumMapper::toResponse)
                .toList();
        }
        public AlbumResponse getAlbumById(Long id){
            albumRepository.findById(id).orElseThrow(()->new RuntimeException("Album not found"));
            return albumMapper.toResponse(albumRepository.findAlbumById(id));
        }
    }

