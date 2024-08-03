package com.example.speedywheels.web.rest;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.dtos.CommentAddDTO;
import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.Car;
import com.example.speedywheels.model.entity.Comment;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.repository.CarRepository;
import com.example.speedywheels.repository.MotorcycleRepository;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.repository.UserRoleRepository;
import com.example.speedywheels.service.UserDetailsServiceImpl;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.speedywheels.TestDataUtils.*;
import static com.example.speedywheels.TestDataUtils.createMotorcycleAddDTO;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CarService carService;
    @Autowired
    private MotorcycleService motorcycleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private MotorcycleRepository motorcycleRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private UserDetails userDetails;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        carRepository.deleteAll();
        motorcycleRepository.deleteAll();
        userRepository.deleteAll();
        user = createUser(passwordEncoder, userRoleRepository);
        userRepository.save(user);
        MockMultipartFile picture1 = getMockMultipartFile();
        userDetails = userDetailsService.loadUserByUsername("testUser");
        CarAddDTO carDTO = createCarAddDTO(picture1);
        carService.saveCar(carDTO, userDetails);
        MotorcycleAddDTO motorcycleDTO = createMotorcycleAddDTO(picture1);
        motorcycleService.saveMotorcycle(motorcycleDTO, userDetails);
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testGetCommentsForCar() throws Exception {
        Comment comment = new Comment();
        comment.setComment("Nice car!");
        comment.setCommentedAt(LocalDateTime.now());
        comment.setUser(user);

        Car car = carRepository.findById(1L).orElseThrow();
        car.getComments().add(comment);
        carRepository.save(car);

        mockMvc.perform(get("/comments/{type}/{vehicleId}","car",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("Nice car!"));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testCreateCommentForCar() throws Exception {
        CommentAddDTO commentDto = createCommentAddDTOForCar();

        mockMvc.perform(post("/comments/add-comment/{type}/{vehicleId}", "car", 1L)
                        .contentType("application/x-www-form-urlencoded")
                        .param("comment", commentDto.getComment())
                        .param("user", commentDto.getUser())
                        .param("commentedAt", commentDto.getCommentedAt().toString())
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/vehicles/vehicle-profile/car/1"));

        mockMvc.perform(get("/comments/{type}/{vehicleId}", "car", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("Great car!"));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testCreateCommentForMotorcycle() throws Exception {
        CommentAddDTO commentDto = createCommentAddDTOForMotorcycle();

        mockMvc.perform(post("/comments/add-comment/{type}/{vehicleId}", "motorcycle", 1L)
                        .contentType("application/x-www-form-urlencoded")
                        .param("comment", commentDto.getComment())
                        .param("user", commentDto.getUser())
                        .param("commentedAt", commentDto.getCommentedAt().toString())
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/vehicles/vehicle-profile/motorcycle/1"));

        mockMvc.perform(get("/comments/{type}/{vehicleId}", "motorcycle", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("Great motorcycle!"));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testGetCommentsForInvalidVehicle() throws Exception {
        mockMvc.perform(get("/comments/{type}/{vehicleId}", "car", 999L))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

}