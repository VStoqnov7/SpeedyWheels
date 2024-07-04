package com.example.speedywheels.model.enums;

public enum Color {
    DARK_BLUE("Dark Blue"),
    BEIGE("Beige"),
    BORDEAUX("Bordeaux"),
    BRONZE("Bronze"),
    WHITE("White"),
    GRAPHITE("Graphite"),
    YELLOW("Yellow"),
    GREEN("Green"),
    GOLDEN("Golden"),
    BROWN("Brown"),
    METALLIC("Metallic"),
    ORANGE("Orange"),
    PINK("Pink"),
    LIGHT_GRAY("Light Gray"),
    LIGHT_BLUE("Light Blue"),
    GRAY("Gray"),
    BLUE("Blue"),
    SILVER("Silver"),
    DARK_GRAY("Dark Gray"),
    DARK_BLUE_METALLIC("Dark Blue Metallic"),
    DARK_RED("Dark Red"),
    CHAMELEON("Chameleon"),
    RED("Red"),
    BLACK("Black");

    private final String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
