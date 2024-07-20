package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.view.LatestEightVehiclesView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.model.view.TheMostPowerfulCarView;
import com.example.speedywheels.repository.CarRepository;
import com.example.speedywheels.service.interfaces.CarService;
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
public class CarServiceImpl implements CarService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(ModelMapper modelMapper, UserService userService, CloudinaryService cloudinaryService, CarRepository carRepository) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.carRepository = carRepository;
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
        this.userService.saveCurrentUser(user);
    }

    @Override
    public TheMostExpensiveVehicleView theMostExpensiveCar() {
        return carRepository.findMostExpensiveCar()
                .stream()
                .findFirst()
                .map(car -> modelMapper.map(car, TheMostExpensiveVehicleView.class))
                .orElse(null);
    }

    @Override
    public TheMostPowerfulCarView theMostPowerfulCar() {
        return carRepository.findMostPowerfulCars()
                .stream()
                .findFirst()
                .map(car -> modelMapper.map(car, TheMostPowerfulCarView.class))
                .orElse(null);
    }

    @Override
    public List<LatestEightVehiclesView> findLatestCars() {
        return carRepository.findLatestCars()
                .stream()
                .map(car -> {
                    LatestEightVehiclesView view = modelMapper.map(car, LatestEightVehiclesView.class);
                    view.setProductionDate(car.getProductionDate().format(formatter));
                    view.setType("car");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Car findById(Long vehicleId) {
        return this.carRepository.findById(vehicleId).orElse(null);
    }

}
