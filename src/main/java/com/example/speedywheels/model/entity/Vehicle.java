package com.example.speedywheels.model.entity;

import com.example.speedywheels.model.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public abstract class Vehicle extends BaseEntity {

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int power;

    @Column(nullable = false)
    private int mileage;

    @Column(nullable = false, name = "production_date")
    private LocalDate productionDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, name = "registered_on")
    private LocalDate registeredOn;

    @Column
    private LocalDate modified;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransmissionType transmission;

    @Column(nullable = false, name = "euro_standard")
    private EuroStandard euroStandard;

    @Column(nullable = false, name = "cubic_capacity")
    private int cubicCapacity;

    @Column(nullable = false, name = "engine_type")
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @ManyToOne
    private User user;

    public Vehicle() {
        this.comments = new LinkedHashSet<>();
    }
}