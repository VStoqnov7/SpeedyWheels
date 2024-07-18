package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotorcycleServiceImpl implements MotorcycleService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public MotorcycleServiceImpl(ModelMapper modelMapper, UserService userService, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void saveMotorcycle(MotorcycleAddDTO motorcycleAddDTO, UserDetails userDetails) {
        Motorcycle motorcycle = modelMapper.map(motorcycleAddDTO, Motorcycle.class);

        List<String> photoUrls = motorcycleAddDTO.getPhotosUrls().stream()
                .map(cloudinaryService::saveImage)
                .collect(Collectors.toList());
        motorcycle.setPhotosUrls(photoUrls);

        motorcycle.setRegisteredOn(LocalDateTime.now());
        User user = this.userService.findByUsername(userDetails.getUsername()).get();
        user.getMyMotorcycles().add(motorcycle);
        this.userService.saveCurrentUser(user);
    }
}
