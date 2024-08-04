package com.example.speedywheels.validation.validPictureSize;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PictureValidatorSize.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPictureSize {
    String message() default "{sizePictureError}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
