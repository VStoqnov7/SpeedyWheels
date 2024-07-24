package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.view.MotorcycleProfileView;
import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MotorcycleService {
    void saveMotorcycle(MotorcycleAddDTO motorcycleAddDTO, UserDetails userDetails);

    TheMostExpensiveVehicleView theMostExpensiveMotorcycle();

    List<VehicleView> findLatestMotorcycles();

    Motorcycle findById(Long vehicleId);

    User findMotorcycleOwner(Long carId);

    boolean availableMotorcycles();

    void removeMotorcycleFromFavorites(Motorcycle motorcycle);

    void saveVehicle(Vehicle vehicle);

    List<VehicleView> findMyFavoriteMotorcycles(String username);

    List<VehicleView> findMyMotorcycles(String username);

    void refreshMotorcycle(Long vehicleId);

    MotorcycleProfileView createMotorcycleProfileView(Motorcycle motorcycle);

    void addFavoriteMotorcycle(Motorcycle motorcycle, User user);

    void deleteFavoriteMotorcycle(Motorcycle motorcycle, User user);

    void deleteMotorcycle(User user, Long vehicleId, Motorcycle motorcycle);
}
