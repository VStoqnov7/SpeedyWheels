package com.example.speedywheels.model.dtos;

import com.example.speedywheels.validation.confirmPassword.ConfirmPasswordForm;
import com.example.speedywheels.validation.email.UniqueEmail;
import com.example.speedywheels.validation.profilePictureValidator.ValidPicture;
import com.example.speedywheels.validation.uniqueUsername.UniqueUsername;
import com.example.speedywheels.validation.validPictureSize.ValidPictureSize;
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
    @Size(min = 5, max = 20, message = "{create.account.size.username}")
    private String username;

    @Size(min = 3, max = 20, message = "{create.account.size.password}")
    private String password;

    @Size(min = 3, max = 20, message = "{create.account.size.password}")
    private String confirmPassword;

    @Size(min = 3, max = 20, message = "{create.account.size.firstName}")
    private String firstName;

    @Size(min = 3, max = 20, message = "{create.account.size.lastName}")
    private String lastName;

    @UniqueEmail
    @Email(message = "{create.account.email.invalid}")
    @NotBlank(message = "{create.account.email.notBlank}")
    private String email;

    @Min(value = 1, message = "{create.account.age.min}")
    private int age;

    @ValidPicture
    @ValidPictureSize
    private MultipartFile picture;
}
