package com.example.speedywheels.init;

import jakarta.annotation.PostConstruct;

public interface DataBaseInitService {
    @PostConstruct
    void init();
}
