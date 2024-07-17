package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.UserRegisterDTO;

public interface UserService {
    void saveUser(UserRegisterDTO userRegisterDTO);

    void dbInitAdmin();

}
