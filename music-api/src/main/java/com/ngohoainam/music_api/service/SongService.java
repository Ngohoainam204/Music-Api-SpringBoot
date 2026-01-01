package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.Mapper.SongMapper;
import com.ngohoainam.music_api.dto.request.songRequest.SongCreateRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongUpdateRequest;

import com.ngohoainam.music_api.dto.response.SongResponse;
import com.ngohoainam.music_api.entity.Album;
import com.ngohoainam.music_api.entity.Artist;
import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.exception.AppException;
import com.ngohoainam.music_api.exception.ErrorCode;
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

    private final ArtistRepository artistRepository;

    private final UserRepository userRepository;
    public Song createSong( SongCreateRequest request){
        Artist artist = artistRepository.findById(request.getArtistId()).orElseThrow(()->
                new AppException(ErrorCode.ARTIST_NOT_FOUND));

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
        return songRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SONG_NOT_FOUND));
    }

    public void deleteSongById(Long id){
        Song song = songRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.SONG_NOT_FOUND));

        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User currentUser = userRepository.findUserByEmail(email).orElseThrow(()->new AppException(ErrorCode.ARTIST_NOT_FOUND));

        boolean isOwner = song.getArtist().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles().name().equals("ADMIN");
        if(!isAdmin || !isOwner) throw new AppException(ErrorCode.FORBIDDEN);
        songRepository.deleteById(id);
    }
    public SongResponse updateSongById(Long id, SongUpdateRequest request) {
        Song song = songRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.SONG_NOT_FOUND));
            songMapper.updateSong(song, request);
            return songMapper.toSongResponse(songRepository.save(song));

        }
    }
