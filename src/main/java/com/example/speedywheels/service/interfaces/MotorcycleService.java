package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.view.LatestEightVehiclesView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MotorcycleService {
    void saveMotorcycle(MotorcycleAddDTO motorcycleAddDTO, UserDetails userDetails);

    TheMostExpensiveVehicleView theMostExpensiveMotorcycle();

    List<LatestEightVehiclesView> findLatestMotorcycles();

    Motorcycle findById(Long vehicleId);

    User findMotorcycleOwner(Long carId);

    boolean availableMotorcycles();

    void deleteMotorcycle(Long vehicleId);

    void removeMotorcycleFromFavorites(Motorcycle motorcycle);

    void saveVehicle(Vehicle vehicle);
}
