package com.example.speedywheels.model.view;


import com.example.speedywheels.model.enums.Color;
import com.example.speedywheels.model.enums.EngineType;
import com.example.speedywheels.model.enums.EuroStandard;
import com.example.speedywheels.model.enums.TransmissionType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class VehicleView {

    private Long id;

    private String brand;

    private String model;

    private int power;

    private int mileage;

    private String productionDate;

    private Color color;

    private String location;

    private LocalDateTime registeredOn;

    private TransmissionType transmission;

    private EuroStandard euroStandard;

    private int cubicCapacity;

    private String price;

    private String type;

    private EngineType engineType;

    private List<String> photosUrls;
}
