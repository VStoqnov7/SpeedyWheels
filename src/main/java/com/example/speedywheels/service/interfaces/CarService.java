package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.model.view.TheMostPowerfulCarView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CarService {
    void saveCar(CarAddDTO carAddDTO, UserDetails userDetails);

    TheMostExpensiveVehicleView theMostExpensiveCar();

    TheMostPowerfulCarView theMostPowerfulCar();

    List<VehicleView> findLatestCars();

    Car findById(Long vehicleId);

    User findCarOwner(Long carId);

    boolean availableCars();

    void deleteCar(Long vehicleId);

    void removeCarFromFavorites(Car car);

    void saveVehicle(Vehicle vehicle);

    List<VehicleView> findJeeps();

    List<VehicleView> findMyFavoriteCars(String username);

    List<VehicleView> findMyCars(String username);

    void refreshCar(Long vehicleId);
}
