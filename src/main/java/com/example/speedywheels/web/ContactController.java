package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.ContactDTO;
import com.example.speedywheels.service.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView sendMessage(@ModelAttribute("contactDTO") ContactDTO contactDTO, ModelAndView model, RedirectAttributes redirectAttributes) {
        String emailContent = "Name: " + contactDTO.getFullName() + "\n" +
                "Email: " + contactDTO.getEmail() + "\n" +
                "Subject: " + contactDTO.getSubject() + "\n" +
                "Message: " + contactDTO.getMessage();

        try {
            emailService.sendEmailFromUser(contactDTO.getSubject(), emailContent);
            redirectAttributes.addFlashAttribute("success", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
        }

        model.setViewName("redirect:/contact-us");
        return model;
    }
}
