package com.example.speedywheels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${email.recipient.address}")
    private String recipientAddress;

    @Value("${spring.mail.username}")
    private String senderAddress;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailFromUser(String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientAddress);
        message.setFrom(senderAddress);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}