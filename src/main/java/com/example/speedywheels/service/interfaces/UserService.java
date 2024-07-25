package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.UserProfileDTO;
import com.example.speedywheels.model.dtos.UserRegisterDTO;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.view.UserProfileView;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserRegisterDTO userRegisterDTO);

    void dbInitAdmin();

    Optional<User> findByUsername(String username);

    void saveCurrentUser(User user);

    List<User> findAll();

    UserProfileDTO mapUserToDTO(String username);

    UserProfileView mapUserToView(String username);

    void updateUser(UserProfileDTO userProfileDTO, String username);
}
