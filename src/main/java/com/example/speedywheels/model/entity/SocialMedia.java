package com.example.speedywheels.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "social_media")
public class SocialMedia extends BaseEntity {

    @Column
    private String github;

    @Column
    private String twitter;

    @Column
    private String instagram;

    @Column
    private String facebook;

    @ManyToOne
    private User user;
}