package com.example.speedywheels.web;

import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@RequestMapping("/user")
public class UserController {
    private final CarService carService;
    private final MotorcycleService motorcycleService;
    private final UserService userService;


    @Autowired
    public UserController(CarService carService, MotorcycleService motorcycleService, UserService userService) {
        this.carService = carService;
        this.motorcycleService = motorcycleService;
        this.userService = userService;
    }

    @GetMapping("/favorite-vehicles")
    public ModelAndView showAllFavoriteVehicles(@PageableDefault(sort = "id", size = 10) Pageable pageable,
                                        ModelAndView model,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        List<VehicleView> cars = carService.findMyFavoriteCars(userDetails.getUsername());
        List<VehicleView> motorcycles = motorcycleService.findMyFavoriteMotorcycles(userDetails.getUsername());
        List<VehicleView> vehicles = Stream.concat(
                        cars.stream(),
                        motorcycles.stream())
                .collect(Collectors.toList());
        Collections.reverse(vehicles);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), vehicles.size());

        Page<VehicleView> allVehicles = new PageImpl<>(
                vehicles.subList(start, end),
                pageable,
                vehicles.size()
        );

        model.addObject("page","favorite");
        model.addObject("favoriteVehicles", allVehicles);
        model.setViewName("my-favorite-vehicles");
        return model;
    }
}
