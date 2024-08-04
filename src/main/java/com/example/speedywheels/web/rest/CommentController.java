package com.example.speedywheels.web.rest;

import com.example.speedywheels.model.dtos.CommentAddDTO;
import com.example.speedywheels.model.entity.Comment;
import com.example.speedywheels.model.entity.Vehicle;
import com.example.speedywheels.model.view.CommentView;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.CommentService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    private final CarService carService;
    private final MotorcycleService motorcycleService;

    @Autowired
    public CommentController(CommentService commentService, CarService carService, MotorcycleService motorcycleService) {
        this.commentService = commentService;
        this.carService = carService;
        this.motorcycleService = motorcycleService;
    }

    @GetMapping("/{type}/{vehicleId}")
    public ResponseEntity<List<CommentView>> getComments(@PathVariable String type, @PathVariable Long vehicleId) {
        Vehicle vehicle = type.equals("car") ? carService.findById(vehicleId) :
                (type.equals("motorcycle") ? motorcycleService.findById(vehicleId) : null);

        if (vehicle == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CommentView> comments = this.commentService.getCommentsForVehicle(vehicle);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/add-comment/{type}/{vehicleId}")
    public ResponseEntity<Void> createComment(@PathVariable String type,
                                              @PathVariable Long vehicleId,
                                              @AuthenticationPrincipal UserDetails userDetails,
                                              @ModelAttribute CommentAddDTO commentDto) {

        Vehicle vehicle = type.equals("car") ? carService.findById(vehicleId) :
                (type.equals("motorcycle") ? motorcycleService.findById(vehicleId) : null);

        this.commentService.createComment(vehicle, userDetails.getUsername(), commentDto);

        if (type.equals("car")) {
            carService.saveVehicle(vehicle);
        } else if (type.equals("motorcycle")) {
            motorcycleService.saveVehicle(vehicle);
        }

        String redirectUrl = String.format("/vehicles/vehicle-profile/%s/%d", type, vehicleId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, redirectUrl);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
