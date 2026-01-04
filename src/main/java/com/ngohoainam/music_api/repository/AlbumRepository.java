package com.ngohoainam.music_api.repository;

import com.ngohoainam.music_api.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album,Long> {
    Album findAlbumById(Long id);
    Album findByTitle(String title);
    Album findByArtist_Name(String name);

}
