package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.Mapper.SongMapper;
import com.ngohoainam.music_api.dto.request.songRequest.SongCreateRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongUpdateRequest;
import com.ngohoainam.music_api.dto.response.ApiResponse;
import com.ngohoainam.music_api.dto.response.SongResponse;
import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.service.SongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private SongService songService;
    @Autowired
    private SongMapper songMapper;

    private final SongMapper songMapper;

    @PreAuthorize("hasRole('ARTIST'/'USER')")
    @PostMapping
    public ApiResponse<SongResponse> createSong(@RequestBody @Valid SongCreateRequest request){
        Song song = songService.createSong(request);
        SongResponse songResponse = songMapper.toSongResponse(song);
        return ApiResponse.<SongResponse>builder()
                .code(200)
                .result(songResponse)
                .message("Create Successful")
                .build();
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<SongResponse> getSongs(){
        return songService.getAllSongs();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ApiResponse<SongResponse> getSongById(@PathVariable("id") Long id){
        Song song = songService.getSongById(id);
        if (song == null) {
            return ApiResponse.<SongResponse>builder()
                    .code(404)
                    .message("Song not found")
                    .build();
        }
        else return ApiResponse.<SongResponse>builder()
                .code(201)
                .result(songMapper.toSongResponse(song))
                .message("Find Successful")
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ApiResponse<SongResponse> updateSongById(@PathVariable("id") Long id,@RequestBody SongUpdateRequest request){
        SongResponse songResponse = songService.updateSongById(id,request);
        if(songResponse == null){
            return ApiResponse.<SongResponse>builder()
                    .code(404)
                    .message("Song not found")
                    .build();
        }
        else {
            return ApiResponse.<SongResponse>builder()
                    .code(202)
                    .result(songResponse)
                    .message("Update Successful")
                    .build();
        }
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ApiResponse<SongResponse> deleteSongById(@PathVariable("id") Long id){
        songService.deleteSongById(id);
        return ApiResponse.<SongResponse>builder()
                .code(204)
                .message("Delete Successful")
                .build();

    }
}
