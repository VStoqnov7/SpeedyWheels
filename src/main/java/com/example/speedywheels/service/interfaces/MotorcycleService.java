package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface MotorcycleService {
    void saveMotorcycle(MotorcycleAddDTO motorcycleAddDTO, UserDetails userDetails);
}
