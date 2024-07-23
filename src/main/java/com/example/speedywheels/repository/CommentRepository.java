package com.example.speedywheels.repository;

import com.example.speedywheels.model.entity.Comment;
import com.example.speedywheels.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}