package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.mapper.SongMapper;
import com.ngohoainam.music_api.dto.request.songRequest.SetPriceSongRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongCreateRequest;
import com.ngohoainam.music_api.dto.request.songRequest.SongUpdateRequest;
import com.ngohoainam.music_api.dto.ApiResponse;
import com.ngohoainam.music_api.dto.response.SongResponse;
import com.ngohoainam.music_api.dto.response.StreamUrlResponse;
import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.service.SongPurchaseService;
import com.ngohoainam.music_api.service.SongService;
import com.ngohoainam.music_api.service.SongStreamingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final SongPurchaseService songPurchaseService;
    private final SongStreamingService songStreamingService;

    private final SongMapper songMapper;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ARTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<SongResponse> createSong(
            @RequestPart("data") @Valid SongCreateRequest request,
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        Song song = songService.createSong(request,file);
        SongResponse songResponse = songMapper.toSongResponse(song);
        return ApiResponse.created(songResponse);
    }

    @GetMapping
    public ApiResponse<List<SongResponse>> getSongs() {
        return ApiResponse.success(songService.getAllSongs());
    }

    @PreAuthorize("hasAnyRole('USER', 'ARTIST', 'ADMIN')")
    @GetMapping("/me")
    public ApiResponse<List<SongResponse>> getMySongs() {
        return ApiResponse.success(songService.getMySongs());
    }

    @GetMapping("/{id}")
    public ApiResponse<SongResponse> getSongById(@PathVariable("id") Long id) {
        Song song = songService.getSongById(id);
        return ApiResponse.success(songMapper.toSongResponse(song));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ARTIST')")
    @PutMapping("/{id}")
    public ApiResponse<SongResponse> updateSongById(@PathVariable("id") Long id, @RequestBody SongUpdateRequest request) {
        SongResponse songResponse = songService.updateSongById(id, request);
        return ApiResponse.success(songResponse);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'ARTIST')")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSongById(@PathVariable("id") Long id) {
        songService.deleteSongById(id);
        return ApiResponse.success("Song Delete Successfully");
    }

    @PatchMapping("/{id}/price")
    @PreAuthorize("hasRole('ARTIST') and @checkOwnerConfig.isSongOwner(authentication,#id)")
    public ApiResponse<SongResponse> updateSongPriceById(@PathVariable("id") Long id, @RequestBody@Valid SetPriceSongRequest request) {
        return ApiResponse.success(songService.setPriceSongById(id,request));
    }

    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasAnyRole('USER', 'ARTIST', 'ADMIN')")
    public ApiResponse<String> purchaseSong(@PathVariable("id") Long id) {
        songPurchaseService.purchaseSong(id);
        return ApiResponse.success("Purchased successfully");
    }

    @GetMapping("/{id}/stream-url")
    @PreAuthorize("hasAnyRole('USER', 'ARTIST', 'ADMIN')")
    public ApiResponse<StreamUrlResponse> getStreamUrl(@PathVariable("id") Long id) {
        return ApiResponse.success(songStreamingService.getStreamUrlBySongId(id));
    }

}


