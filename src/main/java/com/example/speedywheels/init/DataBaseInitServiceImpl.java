package com.example.speedywheels.init;


import com.example.speedywheels.service.interfaces.UserRoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class DataBaseInitServiceImpl implements DataBaseInitService{

    private final UserRoleService userRoleService;

    public DataBaseInitServiceImpl(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    @PostConstruct
    public void init() {
        this.userRoleService.dbInit();
    }
}
