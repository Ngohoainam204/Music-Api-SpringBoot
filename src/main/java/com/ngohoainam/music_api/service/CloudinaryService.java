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

    public Map uploadSong(MultipartFile file) throws IOException {
        return upload(file, "Songs");
    }

    public Map uploadImage(MultipartFile file) throws IOException {
        return upload(file, "Images");
    }

    private Map upload(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        Map params = ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", folder
        );
        return cloudinaryConfig.cloudinary().uploader().upload(file.getBytes(), params);
    }
}


