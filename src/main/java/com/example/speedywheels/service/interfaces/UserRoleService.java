package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.entity.UserRole;
import com.example.speedywheels.model.enums.Role;

public interface UserRoleService {
    void dbInit();

    UserRole findByRole(Role role);
}
