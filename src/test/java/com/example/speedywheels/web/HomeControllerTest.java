package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.view.TheMostExpensiveVehicleView;
import com.example.speedywheels.model.view.TheMostPowerfulCarView;
import com.example.speedywheels.model.view.VehicleView;
import com.example.speedywheels.repository.CarRepository;
import com.example.speedywheels.repository.MotorcycleRepository;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.repository.UserRoleRepository;
import com.example.speedywheels.service.UserDetailsServiceImpl;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.speedywheels.TestDataUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private MotorcycleRepository motorcycleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarService carService;
    @Autowired
    private MotorcycleService motorcycleService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private UserDetails userDetails;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        carRepository.deleteAll();
        motorcycleRepository.deleteAll();
        user = createUser(passwordEncoder,userRoleRepository);
        userRepository.save(user);
        MockMultipartFile picture1 = getMockMultipartFile();

        userDetails = userDetailsService.loadUserByUsername("testUser");

        for (int i = 0; i < 2; i++) {
            CarAddDTO carDTO = createCarAddDTO(picture1, BigDecimal.valueOf(1000 + i * 1000), 1000 + i * 1000);
            carService.saveCar(carDTO, userDetails);
            MotorcycleAddDTO motorcycleDTO = createMotorcycleAddDTO(picture1, BigDecimal.valueOf(1000 + i * 1000), 1000 + i * 1000);
            motorcycleService.saveMotorcycle(motorcycleDTO, userDetails);
        }
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testHome() throws Exception {
        TheMostExpensiveVehicleView mostExpensiveCarView = carService.theMostExpensiveCar();
        TheMostExpensiveVehicleView mostExpensiveMotorcycleView = motorcycleService.theMostExpensiveMotorcycle();
        TheMostPowerfulCarView mostPowerfulCarView = carService.theMostPowerfulCar();

        List<VehicleView> latestEightVehicles = Stream.concat(
                        carService.findLatestCars().stream(),
                        motorcycleService.findLatestMotorcycles().stream())
                .sorted((v1, v2) -> v2.getRegisteredOn().compareTo(v1.getRegisteredOn()))
                .limit(8)
                .collect(Collectors.toList());

        Assertions.assertEquals("2000", carService.findById(2L).getPrice().toString());
        Assertions.assertEquals("2000", motorcycleService.findById(2L).getPrice().toString());
        Assertions.assertEquals(2000, motorcycleService.findById(2L).getPower());
        Assertions.assertEquals(4, carRepository.count() + motorcycleRepository.count());
        Assertions.assertEquals(4, latestEightVehicles.size());
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists(
                        "availableCars",
                        "availableMotorcycles",
                        "theMostExpensiveCarView",
                        "theMostExpensiveMotorcycle",
                        "theMostPowerfulCarView",
                        "latestEightVehicles"))
                .andExpect(model().attribute("theMostExpensiveCarView", Matchers.hasProperty("photosUrls", Matchers.is(mostExpensiveCarView.getPhotosUrls()))))
                .andExpect(model().attribute("theMostExpensiveMotorcycle", Matchers.hasProperty("photosUrls", Matchers.is(mostExpensiveMotorcycleView.getPhotosUrls()))))
                .andExpect(model().attribute("theMostPowerfulCarView", Matchers.hasProperty("brand", Matchers.is(mostPowerfulCarView.getBrand()))));
    }

    @AfterEach
    void cleanUp() {
        carRepository.deleteAll();
        motorcycleRepository.deleteAll();
        userRepository.deleteAll();
    }
}