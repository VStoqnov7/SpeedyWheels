package com.example.speedywheels.validation.confirmPassword;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ConfirmPasswordValidator.class)
public @interface ConfirmPasswordForm {

    String message() default "{confirmPasswordError}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}