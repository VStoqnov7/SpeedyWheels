package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.view.LatestEightVehiclesView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.repository.MotorcycleRepository;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotorcycleServiceImpl implements MotorcycleService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final MotorcycleRepository motorcycleRepository;

    @Autowired
    public MotorcycleServiceImpl(ModelMapper modelMapper, UserService userService, CloudinaryService cloudinaryService, MotorcycleRepository motorcycleRepository) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.motorcycleRepository = motorcycleRepository;
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

    @Override
    public TheMostExpensiveVehicleView theMostExpensiveMotorcycle() {
        return motorcycleRepository.findMostExpensiveMotorcycles()
                .stream()
                .findFirst()
                .map(motorcycle -> modelMapper.map(motorcycle, TheMostExpensiveVehicleView.class))
                .orElse(null);
    }

    @Override
    public List<LatestEightVehiclesView> findLatestMotorcycles() {
        return motorcycleRepository.findLatestMotorcycles()
                .stream()
                .map(motorcycle -> {
                    LatestEightVehiclesView view = modelMapper.map(motorcycle, LatestEightVehiclesView.class);
                    view.setProductionDate(motorcycle.getProductionDate().format(formatter));
                    return view;
                })
                .collect(Collectors.toList());
    }
}
