package com.example.speedywheels.model.view;

import com.example.speedywheels.model.entity.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class UserProfileView {

    private String username;
    private String firstName;
    private String lastName;
    private String contactPhone;
    private String contactEmail;
    private String profilePictureUrl;
    private String city;
    private String socialMediasGithub;
    private String socialMediasTwitter;
    private String socialMediasInstagram;
    private String socialMediasFacebook;
    private Set<UserRole> roles;
}
