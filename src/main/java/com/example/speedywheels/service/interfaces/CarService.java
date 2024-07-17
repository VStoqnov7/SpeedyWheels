package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.CarAddDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface CarService {
    void saveCar(CarAddDTO carAddDTO, UserDetails userDetails);
}
