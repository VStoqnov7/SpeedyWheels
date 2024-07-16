package com.example.speedywheels.service;

import com.example.speedywheels.model.entity.UserRole;
import com.example.speedywheels.model.enums.Role;
import com.example.speedywheels.repository.UserRoleRepository;
import com.example.speedywheels.service.interfaces.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void dbInit() {
        if (userRoleRepository.count() == 0) {
            List<UserRole> roles = Arrays.asList(
                    new UserRole(Role.USER),
                    new UserRole(Role.ADMIN));
            this.userRoleRepository.saveAllAndFlush(roles);
        }
    }

    @Override
    public UserRole findByRole(Role role) {
        return userRoleRepository.findByName(role);
    }
}
