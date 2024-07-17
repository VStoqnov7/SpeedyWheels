package com.example.speedywheels.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/motorcycles")
public class MotorcycleController {

    @GetMapping("/add-motorcycle")
    public ModelAndView showAddMotorcycleForm(ModelAndView model){
        model.setViewName("add-motorcycle");
        return model;
    }
}
