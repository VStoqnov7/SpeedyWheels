package com.example.speedywheels.repository;

import com.example.speedywheels.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByContactEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}