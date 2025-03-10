package com.example.speedywheels.model.entity;

import com.example.speedywheels.model.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
    private LocalDateTime productionDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, name = "registered_on")
    private LocalDateTime registeredOn;

    @Column
    private LocalDateTime modified;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransmissionType transmission;

    @Column(nullable = false, name = "euro_standard")
    @Enumerated(EnumType.STRING)
    private EuroStandard euroStandard;

    @Column(nullable = false, name = "cubic_capacity")
    private int cubicCapacity;

    @Column(nullable = false, name = "engine_type")
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @Column(nullable = false)
    private BigDecimal price;

    @ElementCollection
    private List<String> photosUrls;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Vehicle() {
        this.comments = new ArrayList<>();
        this.photosUrls = new ArrayList<>();
    }
}