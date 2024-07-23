package com.example.speedywheels.model.view;


import com.example.speedywheels.model.enums.EngineType;
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

    private String location;

    private LocalDateTime registeredOn;

    private String price;

    private String type;

    private EngineType engineType;

    private List<String> photosUrls;
}
