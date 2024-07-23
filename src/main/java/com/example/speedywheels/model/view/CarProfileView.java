package com.example.speedywheels.model.view;

import com.example.speedywheels.model.entity.Comment;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.enums.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class CarProfileView {

    private Long id;

    private String brand;

    private String model;

    private int power;

    private int mileage;

    private String productionDate;

    private Color color;

    private String location;

    private String registeredOn;

    private TransmissionType transmission;

    private EuroStandard euroStandard;

    private int cubicCapacity;

    private EngineType engineType;

    private String price;

    private List<String> photosUrls;

    private Set<Comment> comments;

    private CarCategory category;

    private boolean has4x4;

    private boolean hasAirConditioner;

    private boolean hasGPS;

    private Long ownerId;
}
