package com.example.speedywheels.web;

import com.example.speedywheels.model.entity.Contact;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user = createUser();
        userRepository.save(user);
    }

    @Test
    public void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void testOnLoginFailure() throws Exception {
        mockMvc.perform(post("/user/login-error")
                        .param("username", "wrongUser")
                        .param("password", "wrongPass")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("bad_credentials", true));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(post("/user/login")
                        .param("username", "testUser")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    private User createUser() {
        User user = new User()
                .setUsername("testUser")
                .setPassword(passwordEncoder.encode("test"))
                .setFirstName("test")
                .setLastName("User")
                .setAge(22)
                .setProfilePictureUrl("https://bootdey.com/img/Content/avatar/avatar6.png")
                .setBanned(false)
                .setRegisteredOn(LocalDateTime.now())
                .setModified(LocalDateTime.now())
                .setActive(false)
                .setRoles(userRoleRepository.findAll().stream().collect(Collectors.toSet()))
                .setContact(new Contact().setEmail("test@example.com"));
        return user;
    }
}