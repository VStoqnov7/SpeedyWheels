package com.example.speedywheels.util;


import com.example.speedywheels.model.enums.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ModelAttributeUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    public static void addEnumsToCarModel(ModelAndView model) {
        model.addObject("colors", Color.values());
        model.addObject("transmissions", TransmissionType.values());
        model.addObject("categories", CarCategory.values());
        model.addObject("euroStandards", EuroStandard.values());
        model.addObject("engineTypes", EngineType.values());
    }

    public static void addEnumsToMotorcycleModel(ModelAndView model) {
        model.addObject("colors", Color.values());
        model.addObject("transmissions", TransmissionType.values());
        model.addObject("categories", MotorcycleCategory.values());
        model.addObject("euroStandards", EuroStandard.values());
        model.addObject("engineTypes", EngineType.values());
    }
}
