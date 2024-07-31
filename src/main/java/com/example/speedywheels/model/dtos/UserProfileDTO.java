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


    public void setSocialMediasGithub(String socialMediasGithub) {
        this.socialMediasGithub = isEmpty(socialMediasGithub) ? "N/A" : socialMediasGithub;
    }

    public void setSocialMediasTwitter(String socialMediasTwitter) {
        this.socialMediasTwitter = isEmpty(socialMediasTwitter) ? "N/A" : socialMediasTwitter;
    }

    public void setSocialMediasInstagram(String socialMediasInstagram) {
        this.socialMediasInstagram = isEmpty(socialMediasInstagram) ? "N/A" : socialMediasInstagram;
    }

    public void setSocialMediasFacebook(String socialMediasFacebook) {
        this.socialMediasFacebook = isEmpty(socialMediasFacebook) ? "N/A" : socialMediasFacebook;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = isEmpty(contactPhone) ? "N/A" : contactPhone;
    }

    public void setCity(String city) {
        this.city = isEmpty(city) ? "N/A" : city;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
