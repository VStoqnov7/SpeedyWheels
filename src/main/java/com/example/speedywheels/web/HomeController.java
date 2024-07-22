package com.example.speedywheels.web;

import com.example.speedywheels.model.view.LatestEightVehiclesView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.model.view.TheMostPowerfulCarView;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final CarService carService;
    private final MotorcycleService motorcycleService;

    @Autowired
    public HomeController(CarService carService, MotorcycleService motorcycleService) {
        this.carService = carService;
        this.motorcycleService = motorcycleService;
    }

    @GetMapping()
    public ModelAndView home(ModelAndView model){
        TheMostExpensiveVehicleView theMostExpensiveCarView = this.carService.theMostExpensiveCar();
        TheMostExpensiveVehicleView theMostExpensiveMotorcycle = this.motorcycleService.theMostExpensiveMotorcycle();
        TheMostPowerfulCarView theMostPowerfulCarView = this.carService.theMostPowerfulCar();

        List<LatestEightVehiclesView> latestEightCars = this.carService.findLatestCars();
        List<LatestEightVehiclesView> latestEightMotorcycles = this.motorcycleService.findLatestMotorcycles();
        List<LatestEightVehiclesView> latestEightVehicles = Stream.concat(
                        latestEightCars.stream(),
                        latestEightMotorcycles.stream())
                .sorted(Comparator.comparing(LatestEightVehiclesView::getRegisteredOn).reversed())
                .limit(8)
                .collect(Collectors.toList());

        model.addObject("availableCars", this.carService.availableCars());
        model.addObject("availableMotorcycles", this.motorcycleService.availableMotorcycles());
        model.addObject("theMostExpensiveCarView", theMostExpensiveCarView);
        model.addObject("theMostExpensiveMotorcycle", theMostExpensiveMotorcycle);
        model.addObject("theMostPowerfulCarView", theMostPowerfulCarView);
        model.addObject("latestEightVehicles", latestEightVehicles);
        model.setViewName("home");
        return model;
    }
}