package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.Mapper.SongMapper;
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
import java.util.List;
import java.util.Map;
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

        Artist artist = user.getArtist();
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

        Map uploadResult = cloudinaryService.uploadFile(file);

        //Info
        String fileUrl = uploadResult.get("secure_url").toString();
        String format = uploadResult.get("format").toString();
        Long size = Long.parseLong(uploadResult.get("bytes").toString());

        //
        Double durationDouble =(Double) uploadResult.get("duration");
        int durationSeconds  = durationDouble.intValue();
        //
        Object bitRateObj = uploadResult.get("bit_rate");
        int  bitrateKbps = 0;
        if (bitRateObj != null) {
            bitrateKbps = Integer.parseInt(bitRateObj.toString()) / 1000;
        }
        //
        Song song = new Song();
        song.setTitle(request.getTitle());
        song.setDescription(request.getDescription());
        song.setDurationSeconds(durationSeconds);
        //
        song.setExplicit(request.isExplicit());
        song.setSku(request.getSku());
        song.setPublished(request.isPublished());
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
            song.setPriceCents(request.getPrice());
            return songMapper.toSongResponse(songRepository.save(song));
        }
    }
