package com.example.speedywheels.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MotorcycleCategory {
    ATV("enum.motorcycleCategory.ATV"),
    UTV("enum.motorcycleCategory.UTV"),
    BUGGY("enum.motorcycleCategory.Buggy"),
    ENDURO("enum.motorcycleCategory.Enduro"),
    SNOWMOBILE("enum.motorcycleCategory.Snowmobile"),
    CROSS("enum.motorcycleCategory.Cross"),
    SCOOTER("enum.motorcycleCategory.Scooter"),
    TOURER("enum.motorcycleCategory.Tourer"),
    CHOPPER("enum.motorcycleCategory.Chopper"),
    SPORT("enum.motorcycleCategory.Sport"),
    TRIKE("enum.motorcycleCategory.Trike");

    private final String name;

    MotorcycleCategory(String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
