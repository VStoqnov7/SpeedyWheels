package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.UserRegisterDTO;
import com.example.speedywheels.model.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserRegisterDTO userRegisterDTO);

    void dbInitAdmin();

    Optional<User> findByUsername(String username);

    void saveCurrentUser(User user);

    List<User> findAll();
}
