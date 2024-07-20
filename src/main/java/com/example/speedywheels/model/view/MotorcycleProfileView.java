package com.example.speedywheels.model.view;

import com.example.speedywheels.model.entity.Comment;
import com.example.speedywheels.model.enums.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Accessors(chain = true)
public class MotorcycleProfileView {

    private String brand;

    private String model;

    private int power;

    private int mileage;

    private LocalDateTime productionDate;

    private Color color;

    private String location;

    private LocalDateTime registeredOn;

    private TransmissionType transmission;

    private EuroStandard euroStandard;

    private int cubicCapacity;

    private EngineType engineType;

    private BigDecimal price;

    private List<String> photosUrls;

    private Set<Comment> comments;

    private MotorcycleCategory category;

    private boolean hasLuggageCase;

    private boolean hasSidecar;

    private boolean hasStabilityControl;
}
