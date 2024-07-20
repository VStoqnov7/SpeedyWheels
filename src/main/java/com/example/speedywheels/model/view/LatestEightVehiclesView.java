package com.example.speedywheels.model.view;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class LatestEightVehiclesView {

    private Long id;

    private String brand;

    private String productionDate;

    private String location;

    private LocalDateTime registeredOn;

    private BigDecimal price;

    private String type;

    private List<String> photosUrls;
}
