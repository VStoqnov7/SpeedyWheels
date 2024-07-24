package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.service.interfaces.CarService;
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
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @ModelAttribute(name = "carAddDTO")
    public CarAddDTO carAddDTO(){
        return new CarAddDTO();
    }

    @GetMapping("/add-car")
    public ModelAndView showAddCarForm(ModelAndView model){
        ModelAttributeUtil.addEnumsToCarModel(model);
        model.setViewName("add-car");
        return model;
    }


    @PostMapping("/add-car")
    public ModelAndView processAddCarsForm(ModelAndView model,
                                  @Valid CarAddDTO carAddDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal UserDetails userDetails){
        if (bindingResult.hasErrors()){
            ModelAttributeUtil.addEnumsToCarModel(model);
            model.setViewName("add-car");
            return model;
        }

        this.carService.saveCar(carAddDTO,userDetails);
        model.setViewName("redirect:/home");
        return model;
    }
}
