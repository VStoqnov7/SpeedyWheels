package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.util.ModelAttributeUtil;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/motorcycles")
public class MotorcycleController {

    private final MotorcycleService motorcycleService;

    public MotorcycleController(MotorcycleService motorcycleService) {
        this.motorcycleService = motorcycleService;
    }

    @ModelAttribute(name = "motorcycleAddDTO")
    public MotorcycleAddDTO motorcycleAddDTO(){
        return new MotorcycleAddDTO();
    }

    @GetMapping("/add-motorcycle")
    public ModelAndView showAddMotorcycleForm(ModelAndView model){
        ModelAttributeUtil.addEnumsToMotorcycleModel(model);
        model.setViewName("add-motorcycle");
        return model;
    }

    @PostMapping("/add-motorcycle")
    public ModelAndView processAddMotorcyclesForm(ModelAndView model,
                                  @Valid MotorcycleAddDTO motorcycleAddDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal UserDetails userDetails){
        if (bindingResult.hasErrors()){
            ModelAttributeUtil.addEnumsToMotorcycleModel(model);
            model.setViewName("add-motorcycle");
            return model;
        }
        this.motorcycleService.saveMotorcycle(motorcycleAddDTO,userDetails);
        model.setViewName("redirect:/home");
        return model;
    }
}
