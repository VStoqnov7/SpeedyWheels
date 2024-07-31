package com.example.speedywheels.validation.profilePictureValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PictureValidator.class)
public @interface ValidPicture {
    String message() default "{photosError}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
