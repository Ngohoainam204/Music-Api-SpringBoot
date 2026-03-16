package com.ngohoainam.music_api.repository;

import com.ngohoainam.music_api.entity.SongFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongFileRepository extends JpaRepository<SongFile, Long> {
    Optional<SongFile> findTopBySongIdOrderByIdDesc(Long songId);
}


