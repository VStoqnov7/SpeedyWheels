package com.example.speedywheels.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Color {
    DARK_BLUE("enum.color.DarkBlue"),
    BEIGE("enum.color.Beige"),
    BORDEAUX("enum.color.Bordeaux"),
    BRONZE("enum.color.Bronze"),
    WHITE("enum.color.White"),
    GRAPHITE("enum.color.Graphite"),
    YELLOW("enum.color.Yellow"),
    GREEN("enum.color.Green"),
    GOLDEN("enum.color.Golden"),
    BROWN("enum.color.Brown"),
    METALLIC("enum.color.Metallic"),
    ORANGE("enum.color.Orange"),
    PINK("enum.color.Pink"),
    LIGHT_GRAY("enum.color.LightGray"),
    LIGHT_BLUE("enum.color.LightBlue"),
    GRAY("enum.color.Gray"),
    BLUE("enum.color.Blue"),
    SILVER("enum.color.Silver"),
    DARK_GRAY("enum.color.DarkGray"),
    DARK_BLUE_METALLIC("enum.color.DarkBlueMetallic"),
    DARK_RED("enum.color.DarkRed"),
    CHAMELEON("enum.color.Chameleon"),
    RED("enum.color.Red"),
    BLACK("enum.color.Black");

    private final String name;

    Color(String name) {
        this.name = name;
    }


    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
