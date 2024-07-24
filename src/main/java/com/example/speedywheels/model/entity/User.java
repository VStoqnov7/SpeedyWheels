package com.example.speedywheels.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "my_cars",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> myCars;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "my_motorcycles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "motorcycle_id")
    )
    private List<Motorcycle> myMotorcycles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFavoriteCars> favoriteCars;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFavoriteMotorcycle> favoriteMotorcycles;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    public User() {
        this.myCars = new ArrayList<>();
        this.myMotorcycles = new ArrayList<>();
        this.favoriteCars = new ArrayList<>();
        this.favoriteMotorcycles = new ArrayList<>();
        this.roles = new LinkedHashSet<>();
    }
}