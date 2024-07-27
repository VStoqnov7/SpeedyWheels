package com.example.speedywheels.model.view;

import com.example.speedywheels.model.entity.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;
@Getter
@Setter
@Accessors(chain = true)
public class UserControlRoomView {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String contactPhone;
    private String contactEmail;
    private String profilePictureUrl;
    private boolean isBanned;
    private String city;
    private String registeredOn;
    private int vehicleCounts;
    private Set<UserRole> roles;
    public boolean hasRole(String roleName) {
        return roles.stream()
                .anyMatch(role -> role.getName().name().equalsIgnoreCase(roleName));
    }
}
