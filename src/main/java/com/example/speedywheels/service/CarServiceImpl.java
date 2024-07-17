package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    public CarServiceImpl(ModelMapper modelMapper, UserService userService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void saveCar(CarAddDTO carAddDTO, UserDetails userDetails) {
        Car car = modelMapper.map(carAddDTO, Car.class);

        List<String> photoUrls = carAddDTO.getPhotosUrls().stream()
                .map(cloudinaryService::saveImage)
                .collect(Collectors.toList());
        car.setPhotosUrls(photoUrls);

        car.setRegisteredOn(LocalDateTime.now());
        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        user.getMyCars().add(car);
        System.out.println();
        this.userService.saveCurrentUser(user);

    }
}
