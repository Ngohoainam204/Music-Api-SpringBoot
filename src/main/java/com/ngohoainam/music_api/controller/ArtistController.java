package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.dto.request.artistRequest.ArtistCreateRequest;
import com.ngohoainam.music_api.dto.request.artistRequest.ArtistUpdateRequest;
import com.ngohoainam.music_api.dto.ApiResponse;
import com.ngohoainam.music_api.dto.response.ArtistResponse;
import com.ngohoainam.music_api.exception.ErrorCode;
import com.ngohoainam.music_api.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{artists}")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @PostMapping
    public ApiResponse<ArtistResponse> createArtist(@Valid @RequestBody ArtistCreateRequest request) {

        return ApiResponse.success(artistService.createArtist(request));
    }

    @GetMapping()
    public List<ArtistResponse> getArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ApiResponse<ArtistResponse> getArtistById(@PathVariable("id") Long id) {
        return ApiResponse.success(artistService.getArtistById(id));
    }

    @PutMapping( "/{id}")
    public ApiResponse<ArtistResponse> updateArtistById(@RequestBody ArtistUpdateRequest request, @PathVariable("id") Long id) {
        ArtistResponse artistResponse = artistService.updateArtistById(request,id);
        return ApiResponse.success(artistResponse);
    }
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteArtistById(@PathVariable("id") Long id) {
        artistService.deleteArtistById(id);
        return ApiResponse.success(ErrorCode.DELETE_SUCCESS.getMessage());
    }
}
