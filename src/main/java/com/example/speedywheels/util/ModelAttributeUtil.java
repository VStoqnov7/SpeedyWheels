package com.example.speedywheels.util;

import com.example.speedywheels.model.entity.Motorcycle;
import com.example.speedywheels.model.enums.*;
import org.springframework.web.servlet.ModelAndView;

public class ModelAttributeUtil {

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
