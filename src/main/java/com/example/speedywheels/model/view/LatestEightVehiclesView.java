package com.example.speedywheels.model.view;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class LatestEightVehiclesView {

    private String id;

    private String brand;

    private String productionDate;

    private String location;

    private BigDecimal price;

    private List<String> photosUrls;
}
