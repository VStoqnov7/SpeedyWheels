package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.ContactDTO;
import com.example.speedywheels.model.dtos.UserRegisterDTO;
import com.example.speedywheels.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contact-us")
public class ContactController {

    private final EmailService emailService;

    @Autowired
    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @ModelAttribute(name = "contactDTO")
    public ContactDTO contactDTO() {
        return new ContactDTO();
    }

    @GetMapping
    public ModelAndView showContactForm(ModelAndView model) {
        model.setViewName("contact-us");
        return model;
    }

    @PostMapping("/send-message")
    public ModelAndView sendMessage(@ModelAttribute("contactDTO") ContactDTO contactDTO) {
        String emailContent = "Name: " + contactDTO.getFullName() + "\n" +
                "Email: " + contactDTO.getEmail() + "\n" +
                "Subject: " + contactDTO.getSubject() + "\n" +
                "Message: " + contactDTO.getMessage();

        emailService.sendEmailFromUser(contactDTO.getSubject(), emailContent);

        return new ModelAndView("redirect:/contact-us");
    }
}
