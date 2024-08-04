package com.example.speedywheels.validation.validPhotosSize;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhotosValidatorSize.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhotosSize {
    String message() default "{sizePhotosError}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
