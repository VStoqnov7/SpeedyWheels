package com.example.speedywheels.model.entity;

import com.example.speedywheels.model.enums.CarCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "cars")
public class Car extends Vehicle {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarCategory category;

    @Column(nullable = false)
    private boolean has4x4;

    @Column(nullable = false)
    private boolean hasAirConditioner;

    @Column(nullable = false)
    private boolean hasGPS;
}