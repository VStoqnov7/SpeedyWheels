package com.example.speedywheels.model.view;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
@Getter
@Setter
@Accessors(chain = true)
public class TheMostPowerfulCarView {

    private String id;
    private String brand;
    private String model;
    private List<String> photosUrls;
    private boolean has4x4;
    private boolean hasAirConditioner;
    private boolean hasGPS;

}
