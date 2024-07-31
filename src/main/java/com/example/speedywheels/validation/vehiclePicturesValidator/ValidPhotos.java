package com.example.speedywheels.validation.vehiclePicturesValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhotosValidator.class)
public @interface ValidPhotos {
    String message() default "{photosUrlsError}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}