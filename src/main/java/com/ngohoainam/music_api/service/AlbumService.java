package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.Mapper.AlbumMapper;
import com.ngohoainam.music_api.dto.request.albumRequest.AlbumCreateRequest;
import com.ngohoainam.music_api.dto.response.AlbumResponse;
import com.ngohoainam.music_api.entity.Album;
import com.ngohoainam.music_api.entity.Artist;
import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.repository.AlbumRepository;
import com.ngohoainam.music_api.repository.ArtistRepository;
import com.ngohoainam.music_api.repository.SongRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService  {

    private final AlbumRepository albumRepository;

    private final ArtistRepository artistRepository;

    private final SongRepository songRepository;

    private final AlbumMapper albumMapper;

    public AlbumResponse createAlbum(AlbumCreateRequest request){
        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(()->new RuntimeException("Artist not found"));

        Album newAlbum = new Album();
        newAlbum.setTitle(request.getTitle());
        newAlbum.setReleaseDate(request.getReleaseDate());
        newAlbum.setCoverImage(request.getCoverImage());
        newAlbum.setArtist(artist);

        if(request.getSongIds()!= null && !request.getSongIds().isEmpty()){
            Set<Song> songs = request.getSongIds().stream().map(songId->
                    {
                        Song song = songRepository.findById(songId.getId()).orElseThrow(()->new RuntimeException("Song not Found"));
                        song.setAlbum(newAlbum);
                        return song;
                    })
                    .collect(Collectors.toSet());
            newAlbum.setSongs(songs);
            }

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


