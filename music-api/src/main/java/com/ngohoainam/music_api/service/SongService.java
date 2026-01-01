package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.Mapper.SongMapper;
import com.ngohoainam.music_api.dto.request.songRequest.SongCreateRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongUpdateRequest;

import com.ngohoainam.music_api.dto.response.SongResponse;
import com.ngohoainam.music_api.entity.Album;
import com.ngohoainam.music_api.entity.Artist;
import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.repository.AlbumRepository;
import com.ngohoainam.music_api.repository.ArtistRepository;
import com.ngohoainam.music_api.repository.SongRepository;
import com.ngohoainam.music_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongMapper songMapper;

    private final SongRepository songRepository;

    private final AlbumRepository albumRepository;

    private final ArtistRepository artistRepository;

    private final UserRepository userRepository;
    public Song createSong( SongCreateRequest request){
        Artist artist = artistRepository.findById(request.getArtistId()).orElseThrow(()->
                new RuntimeException("Artist not found"));

        Song song = new Song();
        song.setTitle(request.getTitle());
        song.setDurationSeconds(request.getDurationSeconds());
        song.setExplicit(request.isExplicit());
        song.setSku(request.getSku());
        song.setPriceCents(request.getPriceCents());
        song.setPublished(request.isPublished());
        song.setArtist(artist);
        return songRepository.save(song);
    }

    public List<SongResponse> getAllSongs(){
        return songRepository.findAll().stream().map(songMapper::toSongResponse).toList();
    }
    public Song getSongById(Long id){
        return songRepository.findById(id).orElseThrow(()-> new RuntimeException("Song not found"));
    }

    public void deleteSongById(Long id){
        songRepository.findById(id).orElseThrow(()->new RuntimeException("Song not found"));
        songRepository.deleteById(id);
    }
    public SongResponse updateSongById(Long id, SongUpdateRequest request) {
        Song song = songRepository.findById(id).orElseThrow(()->new RuntimeException("Song not found"));
        if (song == null) {
            return null;
        }
        else {
            songMapper.updateSong(song, request);
            return songMapper.toSongResponse(songRepository.save(song));
        }
        }
    }
