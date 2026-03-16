package com.ngohoainam.music_api.repository;

import com.ngohoainam.music_api.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {
    long countByUserId(Long userId);
    Optional<UserDevice> findByUserIdAndDeviceId(Long userId, String deviceId);
    List<UserDevice> findByUserId(Long userId);
    List<UserDevice> findByUserIdOrderByLastLoginDesc(Long userId);
    void deleteByUserIdAndDeviceId(Long userId, String deviceId);
}


