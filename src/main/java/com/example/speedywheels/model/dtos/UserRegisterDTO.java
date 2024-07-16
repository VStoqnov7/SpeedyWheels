package com.example.speedywheels.model.dtos;

import com.example.speedywheels.validation.confirmPassword.ConfirmPasswordForm;
import com.example.speedywheels.validation.email.UniqueEmail;
import com.example.speedywheels.validation.pictureValidator.ValidPicture;
import com.example.speedywheels.validation.uniqueUsername.UniqueUsername;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Accessors(chain = true)
@ConfirmPasswordForm
public class UserRegisterDTO {

    @UniqueUsername
    @Size(min = 5, max = 20, message = "Username length must be between 5 and 20 characters!")
    private String username;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String confirmPassword;

    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20 characters!")
    private String firstName;

    @Size(min = 3, max = 20, message = "Last name length must be between 3 and 20 characters!")
    private String lastName;

    @UniqueEmail
    @Email(message = "Invalid email!")
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @Min(value = 1, message = "Age must be at least 1!")
    private int age;

    @ValidPicture
    private MultipartFile picture;
}
