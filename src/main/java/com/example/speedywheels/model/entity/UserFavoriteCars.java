package com.example.speedywheels.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user_favorite_cars")
public class UserFavoriteCars extends BaseEntity{

    @ManyToOne
    private User user;

    @ManyToOne
    private Car car;

    @Column
    private LocalDateTime addedToFavorite;
}
