package com.example.speedywheels.validation.validPhotosSize;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PhotosValidatorSize implements ConstraintValidator<ValidPhotosSize, List<MultipartFile>> {

    private static final long MAX_SIZE = 10 * 1024 * 1024; // 10MB

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (files == null) {
            return true;
        }

        for (MultipartFile file : files) {
            if (file.getSize() > MAX_SIZE) {
                return false;
            }
        }
        return true;
    }
}
