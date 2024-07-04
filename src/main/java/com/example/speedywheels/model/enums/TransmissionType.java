package com.example.speedywheels.model.enums;

public enum TransmissionType {
    MANUAL("Manual"),
    AUTOMATIC("Automatic");

    private final String name;

    TransmissionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
