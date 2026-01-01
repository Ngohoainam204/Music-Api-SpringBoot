package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.Mapper.SongMapper;
import com.ngohoainam.music_api.dto.request.songRequest.SongCreateRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongUpdateRequest;
import com.ngohoainam.music_api.dto.ApiResponse;
import com.ngohoainam.music_api.dto.response.SongResponse;
import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    private final SongMapper songMapper;

    @PreAuthorize("hasRole('ARTIST'/'USER')")
    @PostMapping
    public ApiResponse<SongResponse> createSong(@RequestBody @Valid SongCreateRequest request) {
        Song song = songService.createSong(request);
        SongResponse songResponse = songMapper.toSongResponse(song);
        return ApiResponse.success(songResponse);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ApiResponse<List<SongResponse>> getSongs() {
        return ApiResponse.success(songService.getAllSongs());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ApiResponse<SongResponse> getSongById(@PathVariable("id") Long id) {
        Song song = songService.getSongById(id);
        return ApiResponse.success(songMapper.toSongResponse(song));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ApiResponse<SongResponse> updateSongById(@PathVariable("id") Long id, @RequestBody SongUpdateRequest request) {
        SongResponse songResponse = songService.updateSongById(id, request);
        return ApiResponse.success(songResponse);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSongById(@PathVariable("id") Long id) {
        songService.deleteSongById(id);
        return ApiResponse.success("Song Delete Successfully");
    }
}
