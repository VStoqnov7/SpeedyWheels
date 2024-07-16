package com.example.speedywheels.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "db1rc9fon",
                "api_key", "243925638474447",
                "api_secret", "tUEAVL6gWzGlllgMDLfHzDZFoH0",
                "secure", true));
    }

    public String saveImage(MultipartFile multipartFile) {
        String imageId = UUID.randomUUID().toString();
        Map<String, Object> params = ObjectUtils.asMap(
                "public_id", imageId,
                "overwrite", true,
                "resource_type", "image");

        File tmpFile = new File(imageId);
        try {
            Files.write(tmpFile.toPath(), multipartFile.getBytes());
            Map<String, Object> uploadResult = cloudinary.uploader().upload(tmpFile, params);
            Files.delete(tmpFile.toPath());
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}