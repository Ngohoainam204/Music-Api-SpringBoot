package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.Mapper.ArtistMapper;
import com.ngohoainam.music_api.dto.request.artistRequest.ArtistCreateRequest;
import com.ngohoainam.music_api.dto.request.artistRequest.ArtistUpdateRequest;
import com.ngohoainam.music_api.dto.response.ApiResponse;
import com.ngohoainam.music_api.dto.response.ArtistResponse;
import com.ngohoainam.music_api.entity.Artist;
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
        ArtistResponse artistResponse = artistService.createArtist(request);
        return ApiResponse.<ArtistResponse>builder()
                .code(200)
                .message("Create Successful")
                .result(artistResponse)
                .build();
    }

    @GetMapping()
    public List<ArtistResponse> getArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ApiResponse<ArtistResponse> getArtistById(@PathVariable("id") Long id) {
        ArtistResponse artistResponse = artistService.getArtistById(id);
        if (artistResponse == null) {
            return ApiResponse.<ArtistResponse>builder()
                    .code(404)
                    .message("Artist not found")
                    .build();
        } else return
                ApiResponse.<ArtistResponse>builder()
                        .code(200)
                        .result(artistResponse)
                        .message("Find Successful")
                        .build();
    }

    @PutMapping( "/{id}")
    public ApiResponse<ArtistResponse> updateArtistById(@RequestBody ArtistUpdateRequest request, @PathVariable("id") Long id) {
        ArtistResponse artistResponse = artistService.updateArtistById(request,id);
        return ApiResponse.<ArtistResponse>builder()
                .code(200)
                .result(artistResponse)
                .message("Update successful")
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<ArtistResponse> deleteArtistById(@PathVariable("id") Long id) {
        ArtistResponse artistResponse = artistService.deleteArtistById(id);
        return ApiResponse.<ArtistResponse>builder()
                .code(200)
                .message("Delete successful")
                .result(artistResponse)
                .build();
    }
}
