package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.UserRegisterDTO;
import com.example.speedywheels.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "userRegisterDTO")
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(ModelAndView model) {
        model.setViewName("create-account");
        return model;
    }

    @PostMapping("/register")
    public ModelAndView processRegistrationForm(ModelAndView model,
                                     @Valid UserRegisterDTO userRegistrationDTO,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.setViewName("create-account");
            return model;
        }

        this.userService.saveUser(userRegistrationDTO);

        model.setViewName("redirect:/");
        return model;
    }
}
