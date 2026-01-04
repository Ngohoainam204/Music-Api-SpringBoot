package com.ngohoainam.music_api.repository;

import com.ngohoainam.music_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
