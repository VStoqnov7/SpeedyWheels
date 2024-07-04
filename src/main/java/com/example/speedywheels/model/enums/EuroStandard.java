package com.example.speedywheels.model.enums;

public enum EuroStandard {
    EURO_1("Euro 1"),
    EURO_2("Euro 2"),
    EURO_3("Euro 3"),
    EURO_4("Euro 4"),
    EURO_5("Euro 5"),
    EURO_6("Euro 6");

    private final String name;

    EuroStandard(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
