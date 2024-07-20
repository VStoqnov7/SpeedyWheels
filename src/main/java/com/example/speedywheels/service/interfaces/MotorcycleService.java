package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.view.LatestEightVehiclesView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MotorcycleService {
    void saveMotorcycle(MotorcycleAddDTO motorcycleAddDTO, UserDetails userDetails);

    TheMostExpensiveVehicleView theMostExpensiveMotorcycle();

    List<LatestEightVehiclesView> findLatestMotorcycles();

    Motorcycle findById(Long vehicleId);
}
