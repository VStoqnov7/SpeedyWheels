package com.example.speedywheels.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Contact contact;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, name = "picture_profile_url")
    private String profilePictureUrl;

    @Column(name = "is_banned")
    private boolean isBanned;

    @Column
    private String city;

    @Column(nullable = false, name = "registered_on")
    private LocalDateTime registeredOn;

    @Column
    private LocalDateTime modified;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    private SocialMedia socialMedias;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Car> myCars;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Motorcycle> myMotorcycles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Car> favoriteCars;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Motorcycle> favoriteMotorcycle;

    @ManyToMany
    private Set<UserRole> roles;

    public User() {
        this.myCars = new LinkedHashSet<>();
        this.myMotorcycles = new LinkedHashSet<>();
        this.favoriteCars = new LinkedHashSet<>();
        this.favoriteMotorcycle = new LinkedHashSet<>();
        this.roles = new LinkedHashSet<>();
    }
}