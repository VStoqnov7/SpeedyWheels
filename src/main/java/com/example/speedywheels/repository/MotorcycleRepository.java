package com.example.speedywheels.repository;

import com.example.speedywheels.model.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
    @Query("SELECT m FROM Motorcycle m ORDER BY m.price DESC")
    List<Motorcycle> findMostExpensiveMotorcycles();

    @Query(value = "SELECT * FROM motorcycles ORDER BY registered_on DESC LIMIT 8", nativeQuery = true)
    List<Motorcycle> findLatestMotorcycles();
}