package com.ngohoainam.music_api.configuration;

import com.ngohoainam.music_api.repository.AlbumRepository;
import com.ngohoainam.music_api.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("checkOwnerConfig")
@RequiredArgsConstructor
public class CheckOwnerConfig {
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    private boolean checkOwnerShip(Authentication auth, String ownerEmail){
        if(auth == null|| !auth.isAuthenticated()) return false;

        boolean isAdmin = auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"));
        if(isAdmin) return true;

        return ownerEmail.equals(auth.getName());
        }

    public boolean isSongOwner(Authentication auth, Long id){
        return songRepository.findById(id)
                .map(song -> checkOwnerShip(auth,song.getArtist().getUser().getEmail()))
                .orElse(false);

    }
    public boolean isAlbumOwner(Authentication auth, Long id){
        return albumRepository.findById(id)
                .map(album -> checkOwnerShip(auth,album.getArtist().getUser().getEmail()) )
                .orElse(false);
    }
    }



