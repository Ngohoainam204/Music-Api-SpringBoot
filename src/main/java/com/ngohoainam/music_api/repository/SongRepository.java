package com.ngohoainam.music_api.repository;

import com.ngohoainam.music_api.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song,Long> {

    Optional<Song> findById(Long id);

}
