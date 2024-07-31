package com.example.speedywheels.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EuroStandard {
    EURO_1("enum.euroStandard.Euro1"),
    EURO_2("enum.euroStandard.Euro2"),
    EURO_3("enum.euroStandard.Euro3"),
    EURO_4("enum.euroStandard.Euro4"),
    EURO_5("enum.euroStandard.Euro5"),
    EURO_6("enum.euroStandard.Euro6");

    private final String name;

    EuroStandard(String name) {
        this.name = name;
    }


    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
