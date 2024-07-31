package com.example.speedywheels.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum TransmissionType {
    MANUAL("enum.transmissionType.Manual"),
    AUTOMATIC("enum.transmissionType.Automatic");

    private final String name;

    TransmissionType(String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
