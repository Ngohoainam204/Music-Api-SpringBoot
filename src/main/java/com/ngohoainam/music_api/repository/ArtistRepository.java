package com.ngohoainam.music_api.repository;

import com.ngohoainam.music_api.entity.Artist;
import com.ngohoainam.music_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long> {
    Optional<Artist> findByUser(User user);
}


