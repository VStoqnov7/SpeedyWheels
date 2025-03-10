package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.UserFavoriteCars;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.enums.CarCategory;
import com.example.speedywheels.model.view.CarProfileView;
import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.model.view.TheMostPowerfulCarView;
import com.example.speedywheels.repository.CarRepository;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.UserService;
import com.example.speedywheels.util.FavoriteUtils;
import com.example.speedywheels.util.ModelAttributeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        car.setOwner(user);
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
    public List<VehicleView> findLatestCars() {
        return carRepository.findLatestCars()
                .stream()
                .map(car -> {
                    VehicleView view = modelMapper.map(car, VehicleView.class);
                    view.setProductionDate(ModelAttributeUtil.formatDate(car.getProductionDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(car.getPrice()));
                    view.setType("car");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Car findById(Long vehicleId) {
        return this.carRepository.findById(vehicleId).orElse(null);
    }

    @Override
    public User findCarOwner(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        return car != null ? car.getOwner() : null;
    }

    @Override
    public boolean availableCars() {
        return this.carRepository.count() > 0;
    }

    @Override
    public void removeCarFromFavorites(Car car) {
        FavoriteUtils.removeCarFromFavorites(userService, car);
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        Car car = modelMapper.map(vehicle, Car.class);
        this.carRepository.save(car);
    }

    @Override
    public List<VehicleView> findJeeps() {
        List<Car> jeeps = carRepository.findByCategory(CarCategory.JEEP);
        return jeeps.stream()
                .map(car -> {
                    VehicleView view = modelMapper.map(car, VehicleView.class);
                    view.setProductionDate(ModelAttributeUtil.formatDate(car.getProductionDate()));
                    view.setType("car");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleView> findMyFavoriteCars(String username) {
        return this.userService.findByUsername(username).get()
                .getFavoriteCars()
                .stream()
                .map(favoriteCar -> {
                    VehicleView view = modelMapper.map(favoriteCar.getCar(), VehicleView.class);
                    view.setProductionDate(ModelAttributeUtil.formatDate(favoriteCar.getCar().getProductionDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(favoriteCar.getCar().getPrice()));
                    view.setAddedToFavorite(favoriteCar.getAddedToFavorite());
                    view.setType("car");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleView> findMyCars(String username) {
        return this.userService.findByUsername(username).get()
                .getMyCars()
                .stream()
                .map(car -> {
                    VehicleView view = modelMapper.map(car, VehicleView.class);
                    view.setProductionDate(ModelAttributeUtil.formatDate(car.getProductionDate()));
                    view.setPrice(ModelAttributeUtil.formatPrice(car.getPrice()));
                    view.setType("car");
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void refreshCar(Long vehicleId) {
        Car car = carRepository.findById(vehicleId).get();
        car.setRegisteredOn(LocalDateTime.now());
        carRepository.save(car);
    }

    @Override
    public CarProfileView createCarProfileView(Car car) {
        CarProfileView carProfileView = modelMapper.map(car, CarProfileView.class);
        carProfileView.setProductionDate(ModelAttributeUtil.formatDate(car.getProductionDate()));
        carProfileView.setRegisteredOn(ModelAttributeUtil.formatDate(car.getRegisteredOn()));
        carProfileView.setPrice(ModelAttributeUtil.formatPrice(car.getPrice()));;
        return carProfileView;
    }

    @Override
    public void addFavoriteCar(Car car, User user) {
        UserFavoriteCars userFavoriteCar = new UserFavoriteCars()
                .setCar(car)
                .setAddedToFavorite(LocalDateTime.now())
                .setUser(user);
        user.getFavoriteCars().add(userFavoriteCar);
    }

    @Override
    public void deleteFavoriteCar(Car car, User user) {
        UserFavoriteCars favorite = user.getFavoriteCars().stream()
                .filter(f -> f.getCar().equals(car))
                .findFirst()
                .orElse(null);

        if (favorite != null) {
            user.getFavoriteCars().remove(favorite);
        }
    }

    @Override
    public void deleteCar(User user, Long vehicleId, Car car) {
        user.getMyCars().remove(car);
        this.removeCarFromFavorites(car);
        this.carRepository.deleteById(vehicleId);
    }
}
