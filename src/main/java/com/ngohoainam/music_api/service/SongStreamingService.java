package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.dto.response.StreamUrlResponse;
import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.entity.SongFile;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.enums.Roles;
import com.ngohoainam.music_api.exception.AppException;
import com.ngohoainam.music_api.exception.ErrorCode;
import com.ngohoainam.music_api.repository.SongFileRepository;
import com.ngohoainam.music_api.repository.SongPurchaseRepository;
import com.ngohoainam.music_api.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SongStreamingService {
    private final SongRepository songRepository;
    private final SongFileRepository songFileRepository;
    private final SongPurchaseRepository songPurchaseRepository;
    private final AuthenticationService authenticationService;

    public StreamUrlResponse getStreamUrlBySongId(Long songId) {
        User currentUser = authenticationService.getCurrentUser();
        Song song = songRepository.findById(songId).orElseThrow(() -> new AppException(ErrorCode.SONG_NOT_FOUND));
        SongFile songFile = songFileRepository.findTopBySongIdOrderByIdDesc(songId)
                .orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_FOUND));

        boolean isFree = song.getPriceCents() == null || song.getPriceCents().compareTo(BigDecimal.ZERO) <= 0;
        boolean isOwner = song.getArtist() != null
                && song.getArtist().getUser() != null
                && song.getArtist().getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles() == Roles.ADMIN;
        boolean purchased = songPurchaseRepository.existsByUserIdAndSongId(currentUser.getId(), songId);

        if (!Boolean.TRUE.equals(song.getIsPublished()) && !isOwner && !isAdmin) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }

        if (!isFree && !purchased && !isOwner && !isAdmin) {
            throw new AppException(ErrorCode.PURCHASE_REQUIRED);
        }

        return StreamUrlResponse.builder()
                .songId(songId)
                .streamUrl(songFile.getStoragePath())
                .purchased(purchased)
                .free(isFree)
                .build();
    }
}
