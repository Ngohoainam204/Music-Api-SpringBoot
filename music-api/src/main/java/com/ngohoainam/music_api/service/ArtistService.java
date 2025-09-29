package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.Mapper.ArtistMapper;
import com.ngohoainam.music_api.dto.request.artistRequest.ArtistCreateRequest;
import com.ngohoainam.music_api.dto.request.artistRequest.ArtistUpdateRequest;
import com.ngohoainam.music_api.dto.response.ArtistResponse;
import com.ngohoainam.music_api.entity.Artist;
import com.ngohoainam.music_api.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    public ArtistResponse createArtist(ArtistCreateRequest request){
        Artist artist = artistMapper.toArtist(request);
        return artistMapper.toDetailResponse(artistRepository.save(artist));
    }
    public List<ArtistResponse> getAllArtists(){
        return artistRepository.findAll().stream()
                .map(artistMapper::toSummaryResponse)
                .toList();

    }

    public ArtistResponse getArtistById(Long id){
        return artistMapper.toDetailResponse(artistRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Artist not found")));
    }
    public ArtistResponse deleteArtistById(Long id){
        Artist artist= artistRepository.findById(id).orElseThrow(()->new RuntimeException("Artist not found"));
        artistRepository.deleteById(id);
        return artistMapper.toDetailResponse(artist);
    }
    public ArtistResponse updateArtistById(ArtistUpdateRequest request, Long id){
        Artist artist = artistRepository.findById(id).orElseThrow(()->new RuntimeException("Artist not found"));

        artistMapper.updateArtist(artist, request);
        return artistMapper.toDetailResponse(artistRepository.save(artist));

    }
}
