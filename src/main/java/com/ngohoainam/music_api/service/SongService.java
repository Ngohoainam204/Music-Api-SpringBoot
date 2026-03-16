package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.mapper.SongMapper;
import com.ngohoainam.music_api.dto.request.songRequest.SetPriceSongRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongCreateRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongUpdateRequest;

import com.ngohoainam.music_api.dto.response.SongResponse;
import com.ngohoainam.music_api.entity.*;
import com.ngohoainam.music_api.exception.AppException;
import com.ngohoainam.music_api.exception.ErrorCode;
import com.ngohoainam.music_api.repository.ArtistRepository;
import com.ngohoainam.music_api.repository.SongFileRepository;
import com.ngohoainam.music_api.repository.SongRepository;
import com.ngohoainam.music_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {

    private final SongMapper songMapper;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final SongFileRepository songFileRepository;
    private final CloudinaryService cloudinaryService;
    private final AuthenticationService authenticationService;

    public Song createSong( SongCreateRequest request, MultipartFile file) throws IOException {
        //Upload
        User user = authenticationService.getCurrentUser();

        // Need to fetch Artist from repository by User
        Artist artist = artistRepository.findByUser(user).orElse(null);
        log.info("displayname" + user.getDisplayName());
        if(artist == null) {
                // Chưa có thì tạo mới ngay lập tức
                artist = new Artist();
                artist.setUser(user);
                artist.setName(user.getDisplayName());
                artist.setBio("New artist");
                log.info("displayname" + artist.getName());
                artist = artistRepository.save(artist);
        }

        Map uploadResult = cloudinaryService.uploadSong(file);

        //Info
        String fileUrl = uploadResult.get("secure_url").toString();
        String format = uploadResult.get("format").toString();
        Long size = Long.parseLong(uploadResult.get("bytes").toString());

        // Safely handle numeric conversion for duration
        Object durationObj = uploadResult.get("duration");
        Long durationSeconds = 0L;
        if (durationObj instanceof Number) {
            durationSeconds = ((Number) durationObj).longValue();
        }

        // Safely handle numeric conversion for bit_rate
        Object bitRateObj = uploadResult.get("bit_rate");
        int  bitrateKbps = 0;
        if (bitRateObj instanceof Number) {
            bitrateKbps = ((Number) bitRateObj).intValue() / 1000;
        }

        //e
        Song song = new Song();
        song.setTitle(request.getTitle());
        song.setDescription(request.getDescription());
        song.setDurationSeconds(durationSeconds);
        //

        song.setSku(request.getSku());
        song.setIsPublished(request.isPublished());
        song.setArtist(artist);
        Song savedSong = songRepository.save(song);

        SongFile songFile = SongFile.builder()
                .song(savedSong)
                .storagePath(fileUrl)
                .storageProvider("cloudinary")
                .format(format)
                .filesizeBytes(size)
                .bitrateKbps(bitrateKbps)
                .build();
        songFileRepository.save(songFile);

        return savedSong;
    }

    public List<SongResponse> getAllSongs(){
        return songRepository.findAll().stream().map(songMapper::toSongResponse).toList();
    }

    public List<SongResponse> getMySongs() {
        User currentUser = authenticationService.getCurrentUser();
        Artist artist = artistRepository.findByUser(currentUser).orElse(null);

        if (artist == null) {
            // If the user is not an artist, they have no songs.
            return Collections.emptyList();
        }

        return songRepository.findByArtist(artist)
                .stream()
                .map(songMapper::toSongResponse)
                .collect(Collectors.toList());
    }

    public Song getSongById(Long id){
        return songRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SONG_NOT_FOUND));
    }

    public void deleteSongById(Long id){
        Song song = songRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.SONG_NOT_FOUND));

        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User currentUser = userRepository.findUserByEmail(email).orElseThrow(()->new AppException(ErrorCode.ARTIST_NOT_FOUND));

        boolean isOwner = song.getArtist().getUser().getId().equals(currentUser.getId());
        
        String roleName = "USER";
        if (currentUser.getRoles() != null) {
            roleName = currentUser.getRoles().name();
        }
        
        boolean isAdmin = roleName.equals("ADMIN");
        if(!isAdmin && !isOwner) throw new AppException(ErrorCode.FORBIDDEN);
        songRepository.deleteById(id);
    }
    public SongResponse updateSongById(Long id, SongUpdateRequest request) {
        Song song = songRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.SONG_NOT_FOUND));
            songMapper.updateSong(song, request);
            return songMapper.toSongResponse(songRepository.save(song));

        }
    public SongResponse setPriceSongById(Long id, SetPriceSongRequest request){
        Song song = songRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.SONG_NOT_FOUND));
        if (request.getPrice() != null) {
            song.setPriceCents(request.getPrice());
        }
        return songMapper.toSongResponse(songRepository.save(song));
    }
}


