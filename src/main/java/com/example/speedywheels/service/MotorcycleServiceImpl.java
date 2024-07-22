package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.view.LatestEightVehiclesView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.repository.MotorcycleRepository;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.service.interfaces.UserService;
import com.example.speedywheels.util.ModelAttributeUtil;
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
        motorcycle.setOwner(user);
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
                    view.setProductionDate(ModelAttributeUtil.formatDate(motorcycle.getProductionDate()));
                    view.setType("motorcycle");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Motorcycle findById(Long vehicleId) {
        return this.motorcycleRepository.findById(vehicleId).orElse(null);
    }

    @Override
    public User findMotorcycleOwner(Long motorcycleId) {
        Motorcycle motorcycle = motorcycleRepository.findById(motorcycleId).orElse(null);
        return motorcycle != null ? motorcycle.getOwner() : null;
    }

    @Override
    public boolean availableMotorcycles() {
        return this.motorcycleRepository.count() > 0;
    }

    @Override
    public void deleteMotorcycle(Long vehicleId) {
        this.motorcycleRepository.deleteById(vehicleId);
    }

    @Override
    public void removeMotorcycleFromFavorites(Motorcycle motorcycle) {
        boolean isFavorite = userService.findAll().stream()
                .anyMatch(u -> u.getFavoriteMotorcycles().contains(motorcycle));

        if (isFavorite) {
            List<User> users = userService.findAll();

            users.forEach(user -> {
                if (user.getFavoriteMotorcycles().contains(motorcycle)) {
                    user.getFavoriteMotorcycles().remove(motorcycle);
                    userService.saveCurrentUser(user);
                }
            });
        }
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        Motorcycle motorcycle = modelMapper.map(vehicle,Motorcycle.class);
        this.motorcycleRepository.save(motorcycle);
    }
}
