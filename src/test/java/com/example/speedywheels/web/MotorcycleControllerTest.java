package com.example.speedywheels.web;

import com.example.speedywheels.TestDataUtils;
import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.repository.MotorcycleRepository;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.repository.UserRoleRepository;
import com.example.speedywheels.service.interfaces.MotorcycleService;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.speedywheels.TestDataUtils.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MotorcycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MotorcycleService motorcycleService;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

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
        motorcycleRepository.deleteAll();
        user = TestDataUtils.createUser(passwordEncoder,userRoleRepository);
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddMotorcycleForm() throws Exception {
        mockMvc.perform(get("/motorcycles/add-motorcycle"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-motorcycle"))
                .andExpect(model().attributeExists("motorcycleAddDTO"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testProcessAddMotorcycleFormWithValidData() throws Exception {
        Assertions.assertEquals(0, motorcycleRepository.count());
        MockMultipartFile picture1 = getMockMultipartFile();
        MotorcycleAddDTO motorcycleAddDTO = createMotorcycleAddDTO(picture1);

        mockMvc.perform(multipart("/motorcycles/add-motorcycle")
                        .file(picture1)
                        .param("brand", motorcycleAddDTO.getBrand())
                        .param("model", motorcycleAddDTO.getModel())
                        .param("power", String.valueOf(motorcycleAddDTO.getPower()))
                        .param("mileage", String.valueOf(motorcycleAddDTO.getMileage()))
                        .param("productionDate", String.valueOf(motorcycleAddDTO.getProductionDate()))
                        .param("color", motorcycleAddDTO.getColor().name())
                        .param("location", motorcycleAddDTO.getLocation())
                        .param("transmission", motorcycleAddDTO.getTransmission().name())
                        .param("euroStandard", motorcycleAddDTO.getEuroStandard().name())
                        .param("cubicCapacity", String.valueOf(motorcycleAddDTO.getCubicCapacity()))
                        .param("engineType", motorcycleAddDTO.getEngineType().name())
                        .param("price", String.valueOf(motorcycleAddDTO.getPrice()))
                        .param("category", motorcycleAddDTO.getCategory().name())
                        .param("hasLuggageCase", String.valueOf(motorcycleAddDTO.isHasLuggageCase()))
                        .param("hasSidecar", String.valueOf((motorcycleAddDTO.isHasSidecar())))
                        .param("hasStabilityControl", String.valueOf((motorcycleAddDTO.isHasStabilityControl())))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Assertions.assertEquals(1, motorcycleRepository.count());
        Assertions.assertEquals(true, motorcycleService.availableMotorcycles());
    }

    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testProcessAddMotorcycleFormWithInvalidData() throws Exception {
        Assertions.assertEquals(0, motorcycleRepository.count());

        MockMultipartFile invalidPicture = new MockMultipartFile(
                "photosUrls",
                "invalid.jpg",
                "image/jpeg",
                new byte[0]);

        MotorcycleAddDTO motorcycleAddDTO = createWrongMotorcycleAddDTO();

        mockMvc.perform(multipart("/motorcycles/add-motorcycle")
                        .file(invalidPicture)
                        .param("brand", motorcycleAddDTO.getBrand())
                        .param("model", motorcycleAddDTO.getModel())
                        .param("power", String.valueOf(motorcycleAddDTO.getPower()))
                        .param("mileage", String.valueOf(motorcycleAddDTO.getMileage()))
                        .param("productionDate", String.valueOf(motorcycleAddDTO.getProductionDate()))
                        .param("color", motorcycleAddDTO.getColor() != null ? motorcycleAddDTO.getColor().name() : "")
                        .param("location", motorcycleAddDTO.getLocation())
                        .param("transmission", motorcycleAddDTO.getTransmission() != null ? motorcycleAddDTO.getTransmission().name() : "")
                        .param("euroStandard", motorcycleAddDTO.getEuroStandard() != null ? motorcycleAddDTO.getEuroStandard().name() : "")
                        .param("cubicCapacity", String.valueOf(motorcycleAddDTO.getCubicCapacity()))
                        .param("engineType", motorcycleAddDTO.getEngineType() != null ? motorcycleAddDTO.getEngineType().name() : "")
                        .param("price", String.valueOf(motorcycleAddDTO.getPrice()))
                        .param("category", motorcycleAddDTO.getCategory() != null ? motorcycleAddDTO.getCategory().name() : "")
                        .param("hasLuggageCase", String.valueOf(motorcycleAddDTO.isHasLuggageCase()))
                        .param("hasSidecar", String.valueOf((motorcycleAddDTO.isHasSidecar())))
                        .param("hasStabilityControl", String.valueOf((motorcycleAddDTO.isHasStabilityControl())))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("add-motorcycle"))
                .andExpect(model().attributeHasFieldErrors("motorcycleAddDTO", "photosUrls", "brand", "power", "mileage", "productionDate", "color", "location", "transmission", "euroStandard", "cubicCapacity", "engineType", "price", "category"));

        Assertions.assertEquals(false, motorcycleService.availableMotorcycles());
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
        motorcycleRepository.deleteAll();
    }
}
