package com.example.speedywheels.scheduler;

import com.example.speedywheels.model.dtos.UserEmailDTO;
import com.example.speedywheels.service.interfaces.EmailService;
import com.example.speedywheels.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MondayEmailScheduler {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public MondayEmailScheduler(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void sendEmailToAllUsers() {
        List<UserEmailDTO> users = this.userService.getAllUserEmails();
        String emailSubject = "Exciting News from Speedy Wheels!";

        users.stream()
                .forEach(userProfile -> {
                    this.emailService.sendEmail(userProfile.getContactEmail(), emailSubject, createEmailBody(userProfile.getFirstName()));
                });
    }

    private String createEmailBody(String name) {
        StringBuilder sb = new StringBuilder();

        sb.append("Hello ").append(name).append(",\n\n")
                .append("We have some great news for you!\n\n")
                .append("🚘 **Speedy Wheels News:** Check out our latest car models and find your favorite one.\n")
                .append("📲 **Visit our site:** Learn more about the latest updates and special offers.\n\n")
                .append("Head over to our site and see what’s new!\n\n")
                .append("Best regards,\n")
                .append("The Speedy Wheels Team");

        return sb.toString();
    }
}
