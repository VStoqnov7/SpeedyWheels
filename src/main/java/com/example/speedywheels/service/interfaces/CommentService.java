package com.example.speedywheels.service.interfaces;

import com.example.speedywheels.model.dtos.CommentAddDTO;
import com.example.speedywheels.model.entity.Comment;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.view.CommentView;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommentService {

    Comment createComment(Vehicle vehicle, String username, CommentAddDTO commentDto);

    List<CommentView> getCommentsForVehicle(Vehicle vehicle);

    CommentView createCommentView(Comment comment);

    Comment findById(Long commentId);
}
