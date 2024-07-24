package com.example.speedywheels.web;

import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.view.CarProfileView;
import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.model.view.MotorcycleProfileView;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.service.interfaces.UserService;
import com.example.speedywheels.util.ModelAttributeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final CarService carService;
    private final MotorcycleService motorcycleService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public VehicleController(CarService carService, MotorcycleService motorcycleService, ModelMapper modelMapper, UserService userService) {
        this.carService = carService;
        this.motorcycleService = motorcycleService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @GetMapping("/all")
    public ModelAndView showAllVehicles(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model) {
        List<VehicleView> cars = carService.findLatestCars();
        List<VehicleView> motorcycles = motorcycleService.findLatestMotorcycles();
        List<VehicleView> vehicles = Stream.concat(
                        cars.stream(),
                        motorcycles.stream())
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
        model.addObject("title", "Vehicle Showcase");
        model.setViewName("vehicles");
        return model;
    }

    @GetMapping("/jeeps")
    public ModelAndView showJeeps(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model) {
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
        model.addObject("title", "Jeep Showcase");
        model.setViewName("vehicles");
        return model;
    }

    @GetMapping("/motorcycles")
    public ModelAndView showMotorcycles(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model) {
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
        model.addObject("title", "Motorcycle Showcase");
        model.setViewName("vehicles");
        return model;
    }

    @GetMapping("/cars")
    public ModelAndView showCars(@PageableDefault(sort = "id", size = 10) Pageable pageable, ModelAndView model) {
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
        model.addObject("title", "Car Showcase");
        model.setViewName("vehicles");
        return model;
    }

    @GetMapping("/vehicle-profile/{type}/{vehicleId}")
    public ModelAndView vehicleProfile(ModelAndView model,
                                       @PathVariable String type,
                                       @PathVariable Long vehicleId,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        boolean vehicleFound = false;
        boolean isFavorite = false;
        boolean isOwner = false;
        User user = this.userService.findByUsername(userDetails.getUsername()).get();

        if (type.equals("car")) {
            Car car = carService.findById(vehicleId);
            if (car != null) {
                CarProfileView carProfileView = modelMapper.map(car, CarProfileView.class);
                carProfileView.setProductionDate(ModelAttributeUtil.formatDate(car.getProductionDate()));
                carProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(car.getRegisteredOn()));
                carProfileView.setPrice(ModelAttributeUtil.formatPrice(car.getPrice()));
                model.addObject("vehicle", carProfileView);
                model.addObject("vehicleType", "car");
                vehicleFound = true;
                isFavorite = user.getFavoriteCars().contains(car) ? true : false;
                isOwner = user.getMyCars().contains(car);
            }
        }

        if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                MotorcycleProfileView motorcycleProfileView = modelMapper.map(motorcycle, MotorcycleProfileView.class);
                motorcycleProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(motorcycle.getRegisteredOn()));
                motorcycleProfileView.setPrice(ModelAttributeUtil.formatPrice(motorcycle.getPrice()));
                model.addObject("vehicle", motorcycleProfileView);
                model.addObject("vehicleType", "motorcycle");
                vehicleFound = true;
                isFavorite = user.getFavoriteMotorcycles().contains(motorcycle) ? true : false;
                isOwner = user.getMyMotorcycles().contains(motorcycle);
            }
        }

        if (!vehicleFound) {
            model.setViewName("error");
            return model;
        }

        model.addObject("page", "profile");
        model.addObject("isFavorite", isFavorite);
        model.addObject("isOwner", isOwner);
        model.addObject("user", userDetails.getUsername());
        model.addObject("isAdmin", userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        model.setViewName("vehicle-profile");
        return model;
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
                user.getFavoriteCars().add(car);
            }
        } else if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                user.getFavoriteMotorcycles().add(motorcycle);
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
                user.getFavoriteCars().remove(car);
            }
        } else if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                user.getFavoriteMotorcycles().remove(motorcycle);
            }
        }
        userService.saveCurrentUser(user);
        model.setViewName(page.equals("profile") ? "redirect:/vehicles/vehicle-profile/" + type + "/" + vehicleId : "redirect:/user/favorite-vehicles");
        return model;
    }

    @DeleteMapping("/delete-vehicle/{type}/{vehicleId}")
    public ModelAndView deleteVehicle(@PathVariable String type,
                                      @PathVariable Long vehicleId,
                                      ModelAndView model) {

        User user = null;
        if (type.equals("car")) {
            Car car = carService.findById(vehicleId);
            if (car != null) {
                user = carService.findCarOwner(vehicleId);
                user.getMyCars().remove(car);
                this.carService.removeCarFromFavorites(car);
                this.carService.deleteCar(vehicleId);
            }
        } else if (type.equals("motorcycle")) {
            Motorcycle motorcycle = motorcycleService.findById(vehicleId);
            if (motorcycle != null) {
                user = motorcycleService.findMotorcycleOwner(vehicleId);
                user.getMyMotorcycles().remove(motorcycle);
                this.motorcycleService.removeMotorcycleFromFavorites(motorcycle);
                this.motorcycleService.deleteMotorcycle(vehicleId);
            }
        }
        userService.saveCurrentUser(user);
        model.setViewName("redirect:/home");
        return model;
    }

}
