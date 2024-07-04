package com.example.speedywheels.model.entity;

import com.example.speedywheels.model.enums.MotorcycleCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "motorcycles")
public class Motorcycle extends Vehicle {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MotorcycleCategory category;

    @Column(nullable = false, name = "has_luggage_case")
    private boolean hasLuggageCase;

    @Column(nullable = false, name = "has_sidecar")
    private boolean hasSidecar;

    @Column(nullable = false, name = "has_stability_control")
    private boolean hasStabilityControl;
}