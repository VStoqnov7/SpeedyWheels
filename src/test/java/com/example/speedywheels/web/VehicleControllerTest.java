package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.*;
import com.example.speedywheels.repository.CarRepository;
import com.example.speedywheels.repository.MotorcycleRepository;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.repository.UserRoleRepository;
import com.example.speedywheels.service.UserDetailsServiceImpl;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.speedywheels.TestDataUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VehicleControllerTest {
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
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowAllVehicles() throws Exception {
        mockMvc.perform(get("/vehicles/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filter", "all"))
                .andExpect(view().name("vehicles"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attribute("vehicles", Matchers.hasProperty("content", Matchers.hasSize(2))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowAllVehiclesWithNoVehicles() throws Exception {
        cleanUp();
        mockMvc.perform(get("/vehicles/all")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vehicles", Matchers.hasProperty("content", Matchers.hasSize(0))));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowJeeps() throws Exception {
        mockMvc.perform(get("/vehicles/jeeps")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filter", "jeeps"))
                .andExpect(view().name("vehicles"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attribute("vehicles", Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowJeepsWithNoJeeps() throws Exception {
        cleanUp();
        mockMvc.perform(get("/vehicles/jeeps")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vehicles", Matchers.hasProperty("content", Matchers.hasSize(0))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowMotorcycles() throws Exception {
        mockMvc.perform(get("/vehicles/motorcycles")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filter", "motorcycles"))
                .andExpect(view().name("vehicles"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attribute("vehicles", Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowMotorcyclesWithNoMotorcycles() throws Exception {
        cleanUp();

        mockMvc.perform(get("/vehicles/motorcycles")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(model().attribute("vehicles", Matchers.hasProperty("content", Matchers.hasSize(0))));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowCars() throws Exception {

        mockMvc.perform(get("/vehicles/cars")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("filter", "cars"))
                .andExpect(view().name("vehicles"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attribute("vehicles", Matchers.hasProperty("content", Matchers.hasSize(1))));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowCarsWithNoCars() throws Exception {
        cleanUp();

        mockMvc.perform(get("/vehicles/cars")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(model().attribute("vehicles", Matchers.hasProperty("content", Matchers.hasSize(0))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testVehicleProfileCar() throws Exception {
        Assertions.assertEquals(1, carRepository.count());

        mockMvc.perform(get("/vehicles/vehicle-profile/{type}/{vehicleId}","car", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicle-profile"))
                .andExpect(model().attribute("vehicleType", "car"))
                .andExpect(model().attribute("vehicle", Matchers.hasProperty("brand", Matchers.is("BMW"))))
                .andExpect(model().attribute("isFavorite", Matchers.is(false)))
                .andExpect(model().attribute("isOwner", Matchers.is(true)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testVehicleProfileMotorcycle() throws Exception {
        Assertions.assertEquals(1, motorcycleRepository.count());

        mockMvc.perform(get("/vehicles/vehicle-profile/{type}/{vehicleId}","motorcycle", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicle-profile"))
                .andExpect(model().attribute("vehicleType", "motorcycle"))
                .andExpect(model().attribute("vehicle", Matchers.hasProperty("brand", Matchers.is("Yamaha"))))
                .andExpect(model().attribute("isFavorite", Matchers.is(false)))
                .andExpect(model().attribute("isOwner", Matchers.is(true)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testVehicleProfileInvalidType() throws Exception {
        mockMvc.perform(get("/vehicles/vehicle-profile/invalidType/{vehicleId}", 1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testVehicleProfileNotFound() throws Exception {
        mockMvc.perform(get("/vehicles/vehicle-profile/car/{vehicleId}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testAddCarToFavorites() throws Exception {
        mockMvc.perform(post("/vehicles/add/car/{vehicleId}",1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vehicles/vehicle-profile/car/1"));

        Assertions.assertEquals(1,user.getFavoriteCars().size());
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testAddMotorcycleToFavorites() throws Exception {
        mockMvc.perform(post("/vehicles/add/motorcycle/{vehicleId}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vehicles/vehicle-profile/motorcycle/1"));

        Assertions.assertEquals(1,user.getMyMotorcycles().size());
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testDeleteFavoriteCar() throws Exception {
        Car car = carRepository.findById(1L).orElseThrow();
        UserFavoriteCars userFavoriteCars = new UserFavoriteCars()
                .setCar(car);
        user.getFavoriteCars().add(userFavoriteCars);
        Assertions.assertEquals(1,user.getFavoriteCars().size());
        mockMvc.perform(delete("/vehicles/delete-favorite-vehicle/car/{vehicleId}/{page}", 1L, "profile")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vehicles/vehicle-profile/car/1"));

        Assertions.assertTrue(user.getFavoriteCars().isEmpty());
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testDeleteFavoriteMotorcycle() throws Exception {
        Car car = carRepository.findById(1L).orElseThrow();
        UserFavoriteCars userFavoriteCars = new UserFavoriteCars()
                .setCar(car);
        user.getFavoriteCars().add(userFavoriteCars);
        Assertions.assertEquals(1,user.getFavoriteCars().size());
        Motorcycle motorcycle = motorcycleRepository.findById(1L).orElseThrow();
        UserFavoriteMotorcycle userFavoriteMotorcycle = new UserFavoriteMotorcycle()
                .setMotorcycle(motorcycle);
        user.getFavoriteMotorcycles().add(userFavoriteMotorcycle);
        Assertions.assertEquals(1,user.getFavoriteMotorcycles().size());
        mockMvc.perform(delete("/vehicles/delete-favorite-vehicle/motorcycle/{vehicleId}/{page}", 1L, "profile")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/vehicles/vehicle-profile/motorcycle/1"));
        Assertions.assertTrue(user.getFavoriteMotorcycles().isEmpty());
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testDeleteFavoriteVehicleRedirectToFavorites() throws Exception {

        Motorcycle motorcycle = motorcycleRepository.findById(1L).orElseThrow();
        UserFavoriteMotorcycle userFavoriteMotorcycle = new UserFavoriteMotorcycle()
                .setMotorcycle(motorcycle);
        user.getFavoriteMotorcycles().add(userFavoriteMotorcycle);
        Assertions.assertEquals(1,user.getFavoriteMotorcycles().size());

        mockMvc.perform(delete("/vehicles/delete-favorite-vehicle/car/{vehicleId}/{page}", 1L, "favorites")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/favorite-vehicles"));

        Assertions.assertTrue(user.getFavoriteCars().isEmpty());

        mockMvc.perform(delete("/vehicles/delete-favorite-vehicle/motorcycle/{vehicleId}/{page}", 1L, "favorites")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/favorite-vehicles"));
        Assertions.assertTrue(user.getFavoriteMotorcycles().isEmpty());
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    void testDeleteCar() throws Exception {
        Assertions.assertTrue(carRepository.existsById(1L));
        mockMvc.perform(delete("/vehicles/delete-vehicle/car/{vehicleId}/{page}", 1L, "profile")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Assertions.assertFalse(carRepository.existsById(1L));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    void testDeleteMotorcycle() throws Exception {
        Assertions.assertTrue(motorcycleRepository.existsById(1L));
        mockMvc.perform(delete("/vehicles/delete-vehicle/motorcycle/{vehicleId}/{page}", 1L, "profile")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Assertions.assertFalse(motorcycleRepository.existsById(1L));
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testDeleteVehicleRedirectToMyVehicles() throws Exception {
        Assertions.assertTrue(carRepository.existsById(1L));
        mockMvc.perform(delete("/vehicles/delete-vehicle/car/{vehicleId}/{page}", 1L, "my-vehicles")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/my-vehicles"));

        Assertions.assertFalse(carRepository.existsById(1L));

        Assertions.assertTrue(motorcycleRepository.existsById(1L));
        mockMvc.perform(delete("/vehicles/delete-vehicle/motorcycle/{vehicleId}/{page}", 1L, "my-vehicles")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/my-vehicles"));

        Assertions.assertFalse(motorcycleRepository.existsById(1L));
    }

    @AfterEach
    void cleanUp() {
        carRepository.deleteAll();
        motorcycleRepository.deleteAll();
        userRepository.deleteAll();
    }

}