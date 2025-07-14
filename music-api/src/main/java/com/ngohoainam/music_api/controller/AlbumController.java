package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.Mapper.AlbumMapper;
import com.ngohoainam.music_api.dto.request.albumRequest.AlbumCreateRequest;
import com.ngohoainam.music_api.dto.response.AlbumResponse;
import com.ngohoainam.music_api.dto.response.ApiResponse;
import com.ngohoainam.music_api.entity.Album;
import com.ngohoainam.music_api.repository.AlbumRepository;
import com.ngohoainam.music_api.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    AlbumMapper albumMapper;
    //
    @PostMapping
    public ApiResponse<AlbumResponse> createAlbum(@RequestBody @Valid AlbumCreateRequest request){
     Album album = albumService.createAlbum(request);
     AlbumResponse albumResponse = albumMapper.toResponse(album);
     return ApiResponse.<AlbumResponse>builder()
             .code(200)
             .result(albumResponse)
             .build();
    }
    @GetMapping
    public List<AlbumResponse> getAllAlbums(){
        return albumService.getAllAlbums();
    }
    @GetMapping("/{id}")
    public AlbumResponse getAlbumById(@PathVariable("id") Long id){
        return albumService.getAlbumById(id);
    }
}
