package com.example.speedywheels.util;

import com.example.speedywheels.model.entity.*;
import com.example.speedywheels.service.interfaces.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteUtils {
    public static void removeCarFromFavorites(UserService userService, Car car) {
        List<User> users = userService.findAll();

        users.forEach(user -> {
            List<UserFavoriteCars> favoritesToRemove = user.getFavoriteCars().stream()
                    .filter(fav -> fav.getCar().equals(car))
                    .collect(Collectors.toList());

            if (!favoritesToRemove.isEmpty()) {
                user.getFavoriteCars().removeAll(favoritesToRemove);
                userService.saveCurrentUser(user);
            }
        });
    }

    public static void removeMotorcycleFromFavorites(UserService userService, Motorcycle motorcycle) {
        List<User> users = userService.findAll();

        users.forEach(user -> {
            List<UserFavoriteMotorcycle> favoritesToRemove = user.getFavoriteMotorcycles().stream()
                    .filter(fav -> fav.getMotorcycle().equals(motorcycle))
                    .collect(Collectors.toList());

            if (!favoritesToRemove.isEmpty()) {
                user.getFavoriteMotorcycles().removeAll(favoritesToRemove);
                userService.saveCurrentUser(user);
            }
        });
    }
}