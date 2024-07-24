package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.*;
import com.example.speedywheels.model.view.MotorcycleProfileView;
import com.example.speedywheels.model.view.VehicleView;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotorcycleServiceImpl implements MotorcycleService {
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
    public List<VehicleView> findLatestMotorcycles() {
        return motorcycleRepository.findLatestMotorcycles()
                .stream()
                .map(motorcycle -> {
                    VehicleView view = modelMapper.map(motorcycle, VehicleView.class);
                    view.setProductionDate(ModelAttributeUtil.formatDate(motorcycle.getProductionDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(motorcycle.getPrice()));
                    view.setType("motorcycle");
                    return view;})
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
    public void removeMotorcycleFromFavorites(Motorcycle motorcycle) {
        List<User> users = userService.findAll();

        users.forEach(user -> {
            List<UserFavoriteMotorcycle> favoritesToRemove = user.getFavoriteMotorcycles().stream()
                    .filter(fav -> fav.getMotorcycle().equals(motorcycle))
                    .collect(Collectors.toList());

            if (!favoritesToRemove.isEmpty()) {
                user.getFavoriteMotorcycles().removeAll(favoritesToRemove);
                userService.saveCurrentUser(user);
            }
        });
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        Motorcycle motorcycle = modelMapper.map(vehicle,Motorcycle.class);
        this.motorcycleRepository.save(motorcycle);
    }

    @Override
    public List<VehicleView> findMyFavoriteMotorcycles(String username) {
        return this.userService.findByUsername(username).get()
                .getFavoriteMotorcycles()
                .stream()
                .map(favoriteMotorcycle -> {
                    VehicleView view = modelMapper.map(favoriteMotorcycle.getMotorcycle(), VehicleView.class);
                    view.setProductionDate(ModelAttributeUtil.formatDate(favoriteMotorcycle.getMotorcycle().getProductionDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(favoriteMotorcycle.getMotorcycle().getPrice()));
                    view.setAddedToFavorite(favoriteMotorcycle.getAddedToFavorite());
                    view.setType("motorcycle");
                    return view;})
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleView> findMyMotorcycles(String username) {
        return this.userService.findByUsername(username).get()
                .getMyMotorcycles()
                .stream()
                .map(motorcycle -> {
                    VehicleView view = modelMapper.map(motorcycle, VehicleView.class);
                    view.setProductionDate(ModelAttributeUtil.formatDate(motorcycle.getProductionDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(motorcycle.getPrice()));
                    view.setType("motorcycle");
                    return view;})
                .collect(Collectors.toList());
    }

    @Override
    public void refreshMotorcycle(Long vehicleId) {
        Motorcycle motorcycle = motorcycleRepository.findById(vehicleId).get();
        motorcycle.setRegisteredOn(LocalDateTime.now());
        motorcycleRepository.save(motorcycle);
    }

    @Override
    public MotorcycleProfileView createMotorcycleProfileView(Motorcycle motorcycle) {
        MotorcycleProfileView motorcycleProfileView = modelMapper.map(motorcycle, MotorcycleProfileView.class);
        motorcycleProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(motorcycle.getRegisteredOn()));
        motorcycleProfileView.setPrice(ModelAttributeUtil.formatPrice(motorcycle.getPrice()));
        return motorcycleProfileView;
    }

    @Override
    public void addFavoriteMotorcycle(Motorcycle motorcycle, User user) {
        UserFavoriteMotorcycle userFavoriteMotorcycle = new UserFavoriteMotorcycle()
                .setMotorcycle(motorcycle)
                .setAddedToFavorite(LocalDateTime.now())
                .setUser(user);
        user.getFavoriteMotorcycles().add(userFavoriteMotorcycle);
    }

    @Override
    public void deleteFavoriteMotorcycle(Motorcycle motorcycle, User user) {
        UserFavoriteMotorcycle favorite = user.getFavoriteMotorcycles().stream()
                .filter(f -> f.getMotorcycle().equals(motorcycle))
                .findFirst()
                .orElse(null);

        if (favorite != null) {
            user.getFavoriteMotorcycles().remove(favorite);
        }
    }

    @Override
    public void deleteMotorcycle(User user, Long vehicleId, Motorcycle motorcycle) {
        user.getMyMotorcycles().remove(motorcycle);
        this.removeMotorcycleFromFavorites(motorcycle);
        this.motorcycleRepository.deleteById(vehicleId);
    }
}
