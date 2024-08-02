package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.CarAddDTO;
import com.example.speedywheels.model.dtos.MotorcycleAddDTO;
import com.example.speedywheels.model.dtos.UserProfileDTO;
import com.example.speedywheels.model.entity.*;
import com.example.speedywheels.model.view.UserProfileView;
import com.example.speedywheels.repository.CarRepository;
import com.example.speedywheels.repository.MotorcycleRepository;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.repository.UserRoleRepository;
import com.example.speedywheels.service.interfaces.CarService;
import com.example.speedywheels.service.interfaces.MotorcycleService;
import com.example.speedywheels.service.interfaces.UserService;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.example.speedywheels.TestDataUtils.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CarService carService;
    @Autowired
    private MotorcycleService motorcycleService;
    @Autowired
    private UserService userService;
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
    private UserDetailsService userDetailsService;
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
    void testShowUserProfile() throws Exception {
        UserProfileView userProfileView = this.userService.mapUserToView(userDetails.getUsername());
        UserProfileDTO userProfileDTO = this.userService.mapUserToDTO(userDetails.getUsername());

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists("userProfileView"))
                .andExpect(model().attributeExists("userProfile"))
                .andExpect(model().attribute("userProfileView", Matchers.hasProperty("username", Matchers.is(userProfileView.getUsername()))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("firstName", Matchers.is(userProfileDTO.getFirstName()))));
        ;
    }


    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testUpdateUserProfileSuccess() throws Exception {
        UserProfileDTO userProfileDTO = createUserProfileDTO();

        mockMvc.perform(post("/user/profile")
                        .flashAttr("userProfile", userProfileDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("firstName", Matchers.is("UpdatedName"))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("lastName", Matchers.is("UpdatedLastName"))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("city", Matchers.is("UpdatedCity"))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("contactPhone", Matchers.is("1234567890"))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("socialMediasGithub", Matchers.is("github.com/updated"))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("socialMediasTwitter", Matchers.is("twitter.com/updated"))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("socialMediasInstagram", Matchers.is("instagram.com/updated"))))
                .andExpect(model().attribute("userProfile", Matchers.hasProperty("socialMediasFacebook", Matchers.is("facebook.com/updated"))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testUpdateUserProfileFailure() throws Exception {
        UserProfileDTO invalidUserProfileDTO = createWrongUserProfileDTO();

        mockMvc.perform(post("/user/profile")
                        .flashAttr("userProfile", invalidUserProfileDTO)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeHasErrors("userProfile"))
                .andExpect(model().attributeHasFieldErrors("userProfile", "firstName"))
                .andExpect(model().attributeHasFieldErrors("userProfile", "lastName"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testUpdateUserProfileWithNoSocialMedia() throws Exception {
        UserProfileDTO userProfileDTO = createUserProfileDTOWithNoSocialMedia();

        mockMvc.perform(post("/user/profile")
                        .flashAttr("userProfile", userProfileDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }


    @Test
    @Transactional
    @DirtiesContext
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowAllFavoriteVehicles() throws Exception {
        Car car = carRepository.findById(1L).orElseThrow();
        UserFavoriteCars userFavoriteCars = new UserFavoriteCars()
                .setCar(car)
                .setAddedToFavorite(LocalDateTime.now());
        user.getFavoriteCars().add(userFavoriteCars);
        Assertions.assertEquals(1,user.getFavoriteCars().size());
        Motorcycle motorcycle = motorcycleRepository.findById(1L).orElseThrow();
        UserFavoriteMotorcycle userFavoriteMotorcycle = new UserFavoriteMotorcycle()
                .setMotorcycle(motorcycle)
                .setAddedToFavorite(LocalDateTime.now());
        user.getFavoriteMotorcycles().add(userFavoriteMotorcycle);
        Assertions.assertEquals(1,user.getFavoriteMotorcycles().size());

        userRepository.save(user);

        mockMvc.perform(get("/user/favorite-vehicles")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-favorite-vehicles"))
                .andExpect(model().attribute("page", "favorite"))
                .andExpect(model().attributeExists("favoriteVehicles"))
                .andExpect(model().attribute("favoriteVehicles", Matchers.hasProperty("content", Matchers.hasSize(2))))
                .andExpect(model().attribute("favoriteVehicles", Matchers.hasProperty("content", Matchers.hasItem(Matchers.hasProperty("id", Matchers.is(car.getId()))))))
                .andExpect(model().attribute("favoriteVehicles", Matchers.hasProperty("content", Matchers.hasItem(Matchers.hasProperty("id", Matchers.is(motorcycle.getId()))))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testShowAllMyVehicles() throws Exception {
        Assertions.assertEquals(1,user.getMyCars().size());
        Assertions.assertEquals(1,user.getMyMotorcycles().size());

        userRepository.save(user);

        mockMvc.perform(get("/user/my-vehicles")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-vehicles"))
                .andExpect(model().attribute("page", "myVehicle"))
                .andExpect(model().attributeExists("myVehicles"))
                .andExpect(model().attribute("myVehicles", Matchers.hasProperty("content", Matchers.hasSize(2))));
    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testRefreshCar() throws Exception {

        Car car = carRepository.findById(1L).orElseThrow();

        LocalDateTime timeBeforeUpdate  = car.getRegisteredOn();

        mockMvc.perform(post("/user/refresh-vehicle/car/{vehicleId}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/my-vehicles"));
        Assertions.assertNotEquals(timeBeforeUpdate ,car.getRegisteredOn());

    }

    @Test
    @Transactional
    @WithMockUser(username = "testUser", roles = {"USER"})
    void testRefreshMotorcycle() throws Exception {

        Motorcycle motorcycle = motorcycleRepository.findById(1L).orElseThrow();

        LocalDateTime timeBeforeUpdate  = motorcycle.getRegisteredOn();

        mockMvc.perform(post("/user/refresh-vehicle/motorcycle/{vehicleId}", 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/my-vehicles"));
        Assertions.assertNotEquals(timeBeforeUpdate ,motorcycle.getRegisteredOn());

    }

    @AfterEach
    void cleanUp() {
        carRepository.deleteAll();
        motorcycleRepository.deleteAll();
        userRepository.deleteAll();
    }


}