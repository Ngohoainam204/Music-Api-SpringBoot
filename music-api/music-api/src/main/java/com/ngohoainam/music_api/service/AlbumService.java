package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.entity.Album;
import com.ngohoainam.music_api.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AlbumService  {
    @Autowired
    private AlbumRepository albumRepository;

    public Album createAlbum(@RequestBody Album album){

    }

}
