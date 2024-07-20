package com.example.speedywheels.web;

import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.view.CarProfileView;
import com.example.speedywheels.model.view.MotorcycleProfileView;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final CarService carService;
    private final MotorcycleService motorcycleService;

    private final ModelMapper modelMapper;

    @Autowired
    public VehicleController(CarService carService, MotorcycleService motorcycleService, ModelMapper modelMapper) {
        this.carService = carService;
        this.motorcycleService = motorcycleService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/vehicle-profile/{type}/{vehicleId}")
    public ModelAndView vehicleProfile(ModelAndView model, @PathVariable String type, @PathVariable Long vehicleId) {
        boolean vehicleFound = false;

        if (type.equals("car")) {
            Car car = carService.findById(vehicleId);
            if (car != null) {
                CarProfileView carProfileView = modelMapper.map(car, CarProfileView.class);
                model.addObject("vehicle", carProfileView);
                model.addObject("vehicleType", "car");
                vehicleFound = true;
            }
        }

        if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                MotorcycleProfileView motorcycleProfileView = modelMapper.map(motorcycle, MotorcycleProfileView.class);
                model.addObject("vehicle", motorcycleProfileView);
                model.addObject("vehicleType", "motorcycle");
                vehicleFound = true;
            }
        }

        if (!vehicleFound) {
            model.setViewName("error");
            return model;
        }

        model.setViewName("vehicle-profile");
        return model;
    }
}
