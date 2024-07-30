package com.example.speedywheels.service.interfaces;

public interface EmailService {
    void sendEmailFromUser(String subject, String text);

    void sendEmail(String toEmail, String subject, String text);
}
