package com.example.speedywheels.web;

import com.example.speedywheels.model.entity.*;
import com.example.speedywheels.model.view.CarProfileView;
import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.model.view.MotorcycleProfileView;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final CarService carService;
    private final MotorcycleService motorcycleService;
    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public VehicleController(CarService carService, MotorcycleService motorcycleService, UserService userService, MessageSource messageSource) {
        this.carService = carService;
        this.motorcycleService = motorcycleService;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/all")
    public ModelAndView showAllVehicles(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model, Locale locale) {
        List<VehicleView> vehicles = Stream.concat(
                        carService.findLatestCars().stream(),
                motorcycleService.findLatestMotorcycles().stream())
                .sorted(Comparator.comparing(VehicleView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), vehicles.size());

        Page<VehicleView> allVehicles = new PageImpl<>(
                vehicles.subList(start, end),
                pageable,
                vehicles.size()
        );

        model.addObject("filter", "all");
        model.addObject("vehicles", allVehicles);
        model.addObject("title", messageSource.getMessage("vehicles.title.all", null, locale));
        model.setViewName("vehicles");
        return model;
    }

    @GetMapping("/jeeps")
    public ModelAndView showJeeps(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model, Locale locale) {
        List<VehicleView> jeeps = carService.findJeeps()
                .stream()
                .sorted(Comparator.comparing(VehicleView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), jeeps.size());

        Page<VehicleView> allVehicles = new PageImpl<>(
                jeeps.subList(start, end),
                pageable,
                jeeps.size()
        );

        model.addObject("filter", "jeeps");
        model.addObject("vehicles", allVehicles);
        model.addObject("title", messageSource.getMessage("vehicles.title.jeep", null, locale));
        model.setViewName("vehicles");
        return model;
    }

    @GetMapping("/motorcycles")
    public ModelAndView showMotorcycles(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model, Locale locale) {
        List<VehicleView> motorcycles = motorcycleService.findLatestMotorcycles()
                .stream()
                .sorted(Comparator.comparing(VehicleView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), motorcycles.size());

        Page<VehicleView> allVehicles = new PageImpl<>(
                motorcycles.subList(start, end),
                pageable,
                motorcycles.size()
        );


        model.addObject("filter", "motorcycles");
        model.addObject("vehicles", allVehicles);
        model.addObject("title", messageSource.getMessage("vehicles.title.motorcycle", null, locale));
        model.setViewName("vehicles");
        return model;
    }

    @GetMapping("/cars")
    public ModelAndView showCars(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model, Locale locale) {
        List<VehicleView> cars = carService.findLatestCars()
                .stream()
                .sorted(Comparator.comparing(VehicleView::getRegisteredOn).reversed())
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), cars.size());

        Page<VehicleView> allVehicles = new PageImpl<>(
                cars.subList(start, end),
                pageable,
                cars.size()
        );

        model.addObject("filter", "cars");
        model.addObject("vehicles", allVehicles);
        model.addObject("title", messageSource.getMessage("vehicles.title.car", null, locale));
        model.setViewName("vehicles");
        return model;
    }

    @GetMapping("/vehicle-profile/{type}/{vehicleId}")
    public ModelAndView vehicleProfile(ModelAndView model,
                                       @PathVariable String type,
                                       @PathVariable Long vehicleId,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        boolean isFavorite = false;
        boolean isOwner = isOwner(vehicleId, user);

        if (type.equals("car")) {
            Car car = carService.findById(vehicleId);
            if (car != null) {
                CarProfileView carProfileView = this.carService.createCarProfileView(car);
                model.addObject("vehicle", carProfileView);
                model.addObject("vehicleType", "car");
                isFavorite = user.getFavoriteCars().stream()
                        .anyMatch(favorite -> favorite.getCar().equals(car));
            }
        }

        if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                MotorcycleProfileView motorcycleProfileView = this.motorcycleService.createMotorcycleProfileView(motorcycle);
                model.addObject("vehicle", motorcycleProfileView);
                model.addObject("vehicleType", "motorcycle");
                isFavorite = user.getFavoriteMotorcycles().stream()
                        .anyMatch(favorite -> favorite.getMotorcycle().equals(motorcycle));
            }
        }

        addObjects(model, userDetails, isFavorite, isOwner);
        model.setViewName("vehicle-profile");
        return model;
    }

    private static void addObjects(ModelAndView model, UserDetails userDetails, boolean isFavorite, boolean isOwner) {
        model.addObject("page", "profile");
        model.addObject("isFavorite", isFavorite);
        model.addObject("isOwner", isOwner);
        model.addObject("user", userDetails.getUsername());
        model.addObject("isAdmin", userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    private boolean isOwner(Long vehicleId, User user) {
        return user.getMyCars().stream().anyMatch(myCar -> myCar.equals(carService.findById(vehicleId))) ||
                user.getMyMotorcycles().stream().anyMatch(myMotorcycle -> myMotorcycle.equals(motorcycleService.findById(vehicleId)));
    }


    @PostMapping("/add/{type}/{vehicleId}")
    public ModelAndView addToFavorite(@PathVariable String type,
                                      @PathVariable Long vehicleId,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      ModelAndView model) {
        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        if (type.equals("car")) {
            Car car = carService.findById(vehicleId);
            if (car != null) {
                this.carService.addFavoriteCar(car,user);
            }
        } else if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                this.motorcycleService.addFavoriteMotorcycle(motorcycle,user);
            }
        }
        userService.saveCurrentUser(user);
        model.setViewName("redirect:/vehicles/vehicle-profile/" + type + "/" + vehicleId);
        return model;
    }

    @DeleteMapping("/delete-favorite-vehicle/{type}/{vehicleId}/{page}")
    public ModelAndView deleteFavoriteVehicle(@PathVariable String type,
                                              @PathVariable Long vehicleId,
                                              @PathVariable String page,
                                              @AuthenticationPrincipal UserDetails userDetails,
                                              ModelAndView model) {
        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        if (type.equals("car")) {
            Car car = carService.findById(vehicleId);
            if (car != null) {
                this.carService.deleteFavoriteCar(car,user);
            }
        } else if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                this.motorcycleService.deleteFavoriteMotorcycle(motorcycle,user);
            }
        }
        userService.saveCurrentUser(user);
        model.setViewName(page.equals("profile") ? "redirect:/vehicles/vehicle-profile/" + type + "/" + vehicleId : "redirect:/user/favorite-vehicles");
        return model;
    }

    @DeleteMapping("/delete-vehicle/{type}/{vehicleId}/{page}")
    public ModelAndView deleteVehicle(@PathVariable String type,
                                      @PathVariable Long vehicleId,
                                      @PathVariable String page,
                                      ModelAndView model) {
        User user = null;
        if (type.equals("car")) {
            Car car = carService.findById(vehicleId);
            if (car != null) {
                user = this.carService.findCarOwner(vehicleId);
                this.carService.deleteCar(user,vehicleId,car);
            }
        } else if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                user = this.motorcycleService.findMotorcycleOwner(vehicleId);
                this.motorcycleService.deleteMotorcycle(user,vehicleId,motorcycle);
            }
        }
        userService.saveCurrentUser(user);
        model.setViewName(page.equals("profile") ? "redirect:/home" : "redirect:/user/my-vehicles");
        return model;
    }

}
