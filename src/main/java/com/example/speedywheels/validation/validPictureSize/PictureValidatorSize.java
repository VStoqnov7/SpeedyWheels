package com.example.speedywheels.validation.validPictureSize;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class PictureValidatorSize implements ConstraintValidator<ValidPictureSize, MultipartFile> {

    private static final long MAX_SIZE = 10 * 1024 * 1024; // 10MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) {
            return true;
        }
        return file.getSize() <= MAX_SIZE;
    }
}