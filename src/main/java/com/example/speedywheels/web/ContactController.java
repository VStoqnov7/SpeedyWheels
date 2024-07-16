package com.example.speedywheels.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ContactController {

    @GetMapping("/contact-us")
    public ModelAndView contact(ModelAndView model) {
        model.setViewName("contact-us");
        return model;
    }
}
