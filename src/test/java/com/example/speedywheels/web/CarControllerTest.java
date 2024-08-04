package com.example.speedywheels.web;


import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.repository.CarRepository;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.repository.UserRoleRepository;
import com.example.speedywheels.service.interfaces.CarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.speedywheels.TestDataUtils.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        carRepository.deleteAll();
        user = createUser(passwordEncoder,userRoleRepository);
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddCarForm() throws Exception {
        mockMvc.perform(get("/cars/add-car"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-car"))
                .andExpect(model().attributeExists("carAddDTO"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testProcessAddCarsFormWithValidData() throws Exception {
        Assertions.assertEquals(0, carRepository.count());
        MockMultipartFile picture1 = getMockMultipartFile();

        CarAddDTO carAddDTO = createCarAddDTO(picture1);

        mockMvc.perform(multipart("/cars/add-car")
                        .file(picture1)
                        .param("brand", carAddDTO.getBrand())
                        .param("model", carAddDTO.getModel())
                        .param("power", String.valueOf(carAddDTO.getPower()))
                        .param("mileage", String.valueOf(carAddDTO.getMileage()))
                        .param("productionDate", String.valueOf(carAddDTO.getProductionDate()))
                        .param("color", carAddDTO.getColor().name())
                        .param("location", carAddDTO.getLocation())
                        .param("transmission", carAddDTO.getTransmission().name())
                        .param("euroStandard", carAddDTO.getEuroStandard().name())
                        .param("cubicCapacity", String.valueOf(carAddDTO.getCubicCapacity()))
                        .param("engineType", carAddDTO.getEngineType().name())
                        .param("price", String.valueOf(carAddDTO.getPrice()))
                        .param("category", carAddDTO.getCategory().name())
                        .param("has4x4", String.valueOf(carAddDTO.isHas4x4()))
                        .param("hasAirConditioner", String.valueOf((carAddDTO.isHasAirConditioner())))
                        .param("hasGPS", String.valueOf((carAddDTO.isHasGPS())))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Assertions.assertEquals(1, carRepository.count());
        Assertions.assertEquals(true, carService.availableCars());
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testProcessAddCarsFormWithInvalidData() throws Exception {
        Assertions.assertEquals(0, carRepository.count());

        MockMultipartFile invalidPicture = new MockMultipartFile(
                "photosUrls",
                "invalid.jpg",
                "image/jpeg",
                new byte[0]);

        CarAddDTO carAddDTO = createWrongCarAddDTO();

        mockMvc.perform(multipart("/cars/add-car")
                        .file(invalidPicture)
                        .param("brand", carAddDTO.getBrand())
                        .param("model", carAddDTO.getModel())
                        .param("power", String.valueOf(carAddDTO.getPower()))
                        .param("mileage", String.valueOf(carAddDTO.getMileage()))
                        .param("productionDate", String.valueOf(carAddDTO.getProductionDate()))
                        .param("color", carAddDTO.getColor() != null ? carAddDTO.getColor().name() : "")
                        .param("location", carAddDTO.getLocation())
                        .param("transmission", carAddDTO.getTransmission() != null ? carAddDTO.getTransmission().name() : "")
                        .param("euroStandard", carAddDTO.getEuroStandard() != null ? carAddDTO.getEuroStandard().name() : "")
                        .param("cubicCapacity", String.valueOf(carAddDTO.getCubicCapacity()))
                        .param("engineType", carAddDTO.getEngineType() != null ? carAddDTO.getEngineType().name() : "")
                        .param("price", String.valueOf(carAddDTO.getPrice()))
                        .param("category", carAddDTO.getCategory() != null ? carAddDTO.getCategory().name() : "")
                        .param("has4x4", String.valueOf(carAddDTO.isHas4x4()))
                        .param("hasAirConditioner", String.valueOf(carAddDTO.isHasAirConditioner()))
                        .param("hasGPS", String.valueOf(carAddDTO.isHasGPS()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("add-car"))
                .andExpect(model().attributeHasFieldErrors("carAddDTO", "photosUrls", "brand", "power", "mileage", "productionDate", "color", "location", "transmission", "euroStandard", "cubicCapacity", "engineType", "price", "category"));

        Assertions.assertEquals(false, carService.availableCars());
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
        carRepository.deleteAll();
    }
}