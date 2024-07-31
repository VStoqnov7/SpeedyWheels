package com.example.speedywheels.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum CarCategory {
    VAN("enum.carCategory.Van"),
    JEEP("enum.carCategory.Jeep"),
    STATION_WAGON("enum.carCategory.StationWagon"),
    COUPE("enum.carCategory.Coupe"),
    MINIVAN("enum.carCategory.Minivan"),
    PICKUP("enum.carCategory.Pickup"),
    SEDAN("enum.carCategory.Sedan"),
    HATCHBACK("enum.carCategory.Hatchback");

    private final String name;

    CarCategory(String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}