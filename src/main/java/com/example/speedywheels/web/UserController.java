package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.UserProfileDTO;
import com.example.speedywheels.model.view.UserProfileView;
import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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


    @GetMapping("/profile")
    public ModelAndView showUserProfile(@AuthenticationPrincipal UserDetails userDetails,
                                       ModelAndView model) {
        UserProfileView userProfileView = this.userService.mapUserToView(userDetails.getUsername());
        UserProfileDTO userProfileDTO = this.userService.mapUserToDTO(userDetails.getUsername());

        model.addObject("userProfileView", userProfileView);
        model.addObject("userProfile", userProfileDTO);

        model.addObject("roles", userProfileView.getRoles().stream()
                .map(role -> role.getName().getName())
                .collect(Collectors.joining(" & ")));
        model.setViewName("user-profile");
        return model;
    }

    @PostMapping("/profile")
    public ModelAndView updateUserProfile(@Valid @ModelAttribute("userProfile") UserProfileDTO userProfileDTO,
                                          BindingResult bindingResult,
                                          ModelAndView model,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            UserProfileView userProfileView = this.userService.mapUserToView(userDetails.getUsername());
            model.addObject("userProfileView", userProfileView);
            model.addObject(userProfileDTO);
            model.setViewName("user-profile");
            return model;
        }

        userService.updateUser(userProfileDTO,userDetails.getUsername());
        model.setViewName("redirect:/user/profile");
        return model;
    }

    @GetMapping("/favorite-vehicles")
    public ModelAndView showAllFavoriteVehicles(@PageableDefault(sort = "id",size = 10) Pageable pageable,
                                        ModelAndView model,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        List<VehicleView> vehicles = Stream.concat(
                        carService.findMyFavoriteCars(userDetails.getUsername()).stream(),
                        motorcycleService.findMyFavoriteMotorcycles(userDetails.getUsername()).stream())
                .sorted(Comparator.comparing(VehicleView::getAddedToFavorite).reversed())
                .collect(Collectors.toList());

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


    @GetMapping("/my-vehicles")
    public ModelAndView showAllMyVehicles(@PageableDefault(sort = "id",size = 10) Pageable pageable,
                                                ModelAndView model,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        List<VehicleView> vehicles = Stream.concat(
                        carService.findMyCars(userDetails.getUsername()).stream(),
                        motorcycleService.findMyMotorcycles(userDetails.getUsername()).stream())
                .sorted(Comparator.comparing(VehicleView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), vehicles.size());

        Page<VehicleView> allMyVehicles = new PageImpl<>(
                vehicles.subList(start, end),
                pageable,
                vehicles.size()
        );

        model.addObject("page", "myVehicle");
        model.addObject("myVehicles", allMyVehicles);
        model.setViewName("my-vehicles");
        return model;
    }

    @PostMapping("/refresh-vehicle/{type}/{vehicleId}")
    public ModelAndView refreshVehicle(ModelAndView model,
                                       @PathVariable String type,
                                       @PathVariable Long vehicleId) {
        if (type.equals("car")){
            this.carService.refreshCar(vehicleId);
        }else {
            this.motorcycleService.refreshMotorcycle(vehicleId);
        }
        model.setViewName("redirect:/user/my-vehicles");
        return model;
    }

}
