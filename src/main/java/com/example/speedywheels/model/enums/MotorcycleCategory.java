package com.example.speedywheels.model.enums;

public enum MotorcycleCategory {
    ATV("ATV"),
    UTV("UTV"),
    BUGGY("Buggy"),
    ENDURO("Enduro"),
    SNOWMOBILE("Snowmobile"),
    CROSS("Cross"),
    SCOOTER("Scooter"),
    TOURER("Tourer"),
    CHOPPER("Chopper");

    private final String name;

    MotorcycleCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
