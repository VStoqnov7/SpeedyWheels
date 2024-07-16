package com.example.speedywheels.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class LoginController {

    @GetMapping("/login")
    public ModelAndView login(ModelAndView model) {
        model.setViewName("login");
        return model;
    }
}
