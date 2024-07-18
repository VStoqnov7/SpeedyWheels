package com.example.speedywheels.repository;

import com.example.speedywheels.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c ORDER BY c.price DESC")
    List<Car> findMostExpensiveCar();

    @Query("SELECT c FROM Car c ORDER BY c.power DESC")
    List<Car> findMostPowerfulCars();

    @Query(value = "SELECT * FROM cars ORDER BY registered_on DESC LIMIT 8", nativeQuery = true)
    List<Car> findLatestCars();
}