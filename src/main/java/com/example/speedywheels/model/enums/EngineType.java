package com.example.speedywheels.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EngineType {
    PETROL("enum.engine.Petrol"),
    DIESEL("enum.engine.Diesel"),
    ELECTRIC("enum.engine.Electric"),
    HYBRID("enum.engine.Hybrid"),
    GAS("enum.engine.Gas");

    private final String name;

    EngineType(String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}

