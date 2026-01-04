package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.Mapper.AlbumMapper;
import com.ngohoainam.music_api.dto.request.albumRequest.AlbumCreateRequest;
import com.ngohoainam.music_api.dto.response.AlbumResponse;
import com.ngohoainam.music_api.dto.ApiResponse;
import com.ngohoainam.music_api.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
     AlbumResponse albumResponse = albumService.createAlbum(request);
     return ApiResponse.created(albumResponse);
    }
    @GetMapping
    public List<AlbumResponse> getAllAlbums(){
        return albumService.getAllAlbums();
    }
    @GetMapping("/{id}")
    public ApiResponse<AlbumResponse> getAlbumById(@PathVariable("id") Long id){
        return ApiResponse.success(albumService.getAlbumById(id));
    }
}
