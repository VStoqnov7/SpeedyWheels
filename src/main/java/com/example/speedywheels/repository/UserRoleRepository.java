package com.example.speedywheels.repository;

import com.example.speedywheels.model.entity.UserRole;
import com.example.speedywheels.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(Role role);
}