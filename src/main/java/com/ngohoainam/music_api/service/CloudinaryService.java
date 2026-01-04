package com.ngohoainam.music_api.service;

import com.cloudinary.utils.ObjectUtils;
import com.ngohoainam.music_api.configuration.CloudinaryConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final CloudinaryConfig cloudinaryConfig;

    public Map uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) return null;

        Map params = ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", "SongList"
        );
        return cloudinaryConfig.cloudinary().uploader().upload(file.getBytes(), params);
    }
}
