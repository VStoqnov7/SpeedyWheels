package com.example.speedywheels.web;

import com.example.speedywheels.model.dtos.UserRegisterDTO;
import com.example.speedywheels.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private UserRegisterDTO userRegisterDTO;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRegisterDTO = createUser();
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-account"))
                .andExpect(model().attributeExists("userRegisterDTO"));
        ;
    }

    @Test
    void testRegistration() throws Exception {
        Assertions.assertEquals(0, userRepository.count());
        String imagePath = "src/test/resources/static/images/test.jpeg";
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

        MockMultipartFile picture = new MockMultipartFile(
                "picture",
                "profile.jpg",
                "image/jpeg",
                imageBytes);

        mockMvc.perform(multipart("/user/register")
                        .file(picture)
                        .param("username", userRegisterDTO.getUsername())
                        .param("email", userRegisterDTO.getEmail())
                        .param("firstName", userRegisterDTO.getFirstName())
                        .param("lastName", userRegisterDTO.getLastName())
                        .param("age", String.valueOf(userRegisterDTO.getAge()))
                        .param("password", userRegisterDTO.getPassword())
                        .param("confirmPassword", userRegisterDTO.getConfirmPassword())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Assertions.assertEquals(1, userRepository.count());
        Assertions.assertNotNull(userRepository.findByUsername("testUser").get());
        Assertions.assertEquals("test@example.com", userRepository.findByUsername("testUser").get().getContact().getEmail());
    }

    @Test
    void testRegistrationWithWrongUser() throws Exception {
        Assertions.assertEquals(0, userRepository.count());
        MockMultipartFile invalidPicture = new MockMultipartFile(
                "picture",
                "invalid.jpg",
                "image/jpeg",
                new byte[0]);

        mockMvc.perform(multipart("/user/register")
                        .file(invalidPicture)
                        .param("username", "")
                        .param("email", "")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("age", "0")
                        .param("password", "")
                        .param("confirmPassword", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("create-account"))
                .andExpect(model().attributeHasFieldErrors("userRegisterDTO", "username", "email", "firstName", "lastName", "age", "password", "confirmPassword"));

        Assertions.assertEquals(0, userRepository.count());
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    private UserRegisterDTO createUser() {
        UserRegisterDTO registerDTO = new UserRegisterDTO()
                .setUsername("testUser")
                .setEmail("test@example.com")
                .setFirstName("Test")
                .setLastName("User")
                .setAge(22)
                .setPassword("password123")
                .setConfirmPassword("password123");
        return registerDTO;
    }

}