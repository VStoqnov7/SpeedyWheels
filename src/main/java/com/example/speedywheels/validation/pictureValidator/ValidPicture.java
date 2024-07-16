package com.example.speedywheels.validation.pictureValidator;

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
    String message() default "Picture must not be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
