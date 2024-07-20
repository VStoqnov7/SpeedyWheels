package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.view.LatestEightVehiclesView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.model.view.TheMostPowerfulCarView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CarService {
    void saveCar(CarAddDTO carAddDTO, UserDetails userDetails);

    TheMostExpensiveVehicleView theMostExpensiveCar();

    TheMostPowerfulCarView theMostPowerfulCar();

    List<LatestEightVehiclesView> findLatestCars();

    Car findById(Long vehicleId);
}
