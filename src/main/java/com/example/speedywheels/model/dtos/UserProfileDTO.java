package com.example.speedywheels.model.dtos;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserProfileDTO {

    @Size(min = 3, max = 20, message = "{create.account.size.firstName}")
    private String firstName;

    @Size(min = 3, max = 20, message = "{create.account.size.lastName}")
    private String lastName;

    private String contactPhone;
    private String city;
    private String socialMediasGithub;
    private String socialMediasTwitter;
    private String socialMediasInstagram;
    private String socialMediasFacebook;


    public UserProfileDTO  setSocialMediasGithub(String socialMediasGithub) {
        this.socialMediasGithub = isEmpty(socialMediasGithub) ? "N/A" : socialMediasGithub;
        return this;
    }

    public UserProfileDTO  setSocialMediasTwitter(String socialMediasTwitter) {
        this.socialMediasTwitter = isEmpty(socialMediasTwitter) ? "N/A" : socialMediasTwitter;
        return this;
    }

    public UserProfileDTO  setSocialMediasInstagram(String socialMediasInstagram) {
        this.socialMediasInstagram = isEmpty(socialMediasInstagram) ? "N/A" : socialMediasInstagram;
        return this;
    }

    public UserProfileDTO  setSocialMediasFacebook(String socialMediasFacebook) {
        this.socialMediasFacebook = isEmpty(socialMediasFacebook) ? "N/A" : socialMediasFacebook;
        return this;
    }

    public UserProfileDTO  setContactPhone(String contactPhone) {
        this.contactPhone = isEmpty(contactPhone) ? "N/A" : contactPhone;
        return this;
    }

    public UserProfileDTO  setCity(String city) {
        this.city = isEmpty(city) ? "N/A" : city;
        return this;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
