package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.entity.SongPurchase;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.exception.AppException;
import com.ngohoainam.music_api.exception.ErrorCode;
import com.ngohoainam.music_api.repository.SongPurchaseRepository;
import com.ngohoainam.music_api.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SongPurchaseService {
    private final SongRepository songRepository;
    private final SongPurchaseRepository songPurchaseRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public void purchaseSong(Long songId) {
        User currentUser = authenticationService.getCurrentUser();
        Song song = songRepository.findById(songId).orElseThrow(() -> new AppException(ErrorCode.SONG_NOT_FOUND));

        BigDecimal price = song.getPriceCents() == null ? BigDecimal.ZERO : song.getPriceCents();
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        boolean purchased = songPurchaseRepository.existsByUserIdAndSongId(currentUser.getId(), songId);
        if (purchased) {
            return;
        }

        SongPurchase songPurchase = SongPurchase.builder()
                .user(currentUser)
                .song(song)
                .amountCents(price)
                .build();
        songPurchaseRepository.save(songPurchase);
    }
}
