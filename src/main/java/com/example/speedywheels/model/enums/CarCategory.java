package com.example.speedywheels.model.enums;

public enum CarCategory {
    VAN("Van"),
    JEEP("Jeep"),
    STATION_WAGON("Station Wagon"),
    COUPE("Coupe"),
    MINIVAN("Minivan"),
    PICKUP("Pickup"),
    SEDAN("Sedan"),
    HATCHBACK("Hatchback");

    private final String name;

    CarCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}