package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.CommentAddDTO;
import com.example.speedywheels.model.entity.Comment;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.view.CommentView;
import com.example.speedywheels.repository.CommentRepository;
import com.example.speedywheels.service.interfaces.CommentService;
import com.example.speedywheels.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin("*")
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, UserService userService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public Comment createComment(Vehicle vehicle, String username, CommentAddDTO commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        vehicle.getComments().add(comment);

        User user = this.userService.findByUsername(username).get();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        comment.setCommentedAt(LocalDateTime.now());
        comment.setUser(user);

        return comment;
    }

    @Override
    public List<CommentView> getCommentsForVehicle(Vehicle vehicle) {
        List<Comment> comments = vehicle.getComments();
        Collections.reverse(comments);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentView.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentView createCommentView(Comment comment) {
        return modelMapper.map(comment,CommentView.class);
    }

    @Override
    public Comment findById(Long commentId) {
        return this.commentRepository.findById(commentId).orElse(null);
    }

}
