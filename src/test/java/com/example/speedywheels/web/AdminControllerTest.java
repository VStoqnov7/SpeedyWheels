package com.example.speedywheels.web;

import com.example.speedywheels.model.entity.Contact;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.enums.Role;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.example.speedywheels.TestDataUtils.*;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setUp(){
        User user1 = createUser(passwordEncoder,userRoleRepository);

        User user2 = new User()
                .setUsername("testUser2")
                .setPassword(passwordEncoder.encode("test"))
                .setFirstName("test2")
                .setLastName("User2")
                .setAge(22)
                .setProfilePictureUrl("https://bootdey.com/img/Content/avatar/avatar6.png")
                .setBanned(false)
                .setRegisteredOn(LocalDateTime.now())
                .setModified(LocalDateTime.now())
                .setActive(true)
                .setRoles(userRoleRepository.findById(1L).stream().collect(Collectors.toSet()))
                .setContact(new Contact().setEmail("test@example2.com"));

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowControlRoom() throws Exception {
        mockMvc.perform(get("/control-room"))
                .andExpect(status().isOk())
                .andExpect(view().name("control-room"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(2)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddAdminRole() throws Exception {
        User user = userRepository.findById(3L).orElseThrow();
        Assertions.assertFalse(user.getRoles().stream()
                .anyMatch(role -> role.getName() == Role.ADMIN));
        mockMvc.perform(post("/control-room/add-admin/{userId}", 3L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/control-room"));
        Assertions.assertTrue(user.getRoles().stream()
                .anyMatch(role -> role.getName() == Role.ADMIN));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testRemoveAdminRole() throws Exception {
        User user = userRepository.findById(2L).orElseThrow();
        Assertions.assertTrue(user.getRoles().stream()
                .anyMatch(role -> role.getName() == Role.ADMIN));

        mockMvc.perform(post("/control-room/remove-admin/{userId}", 2L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/control-room"));

        Assertions.assertFalse(user.getRoles().stream()
                .anyMatch(role -> role.getName() == Role.ADMIN));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testBlockUser() throws Exception {
        Assertions.assertFalse(userRepository.findById(3L).orElseThrow().isBanned());
        mockMvc.perform(post("/control-room/block-user/{userId}", 3L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/control-room"));

        Assertions.assertTrue(userRepository.findById(3L).orElseThrow().isBanned());
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUnblockUser() throws Exception {
        Assertions.assertFalse(userRepository.findById(3L).orElseThrow().isBanned());
        User user = userRepository.findById(3L).orElseThrow();
        user.setBanned(true);
        userRepository.save(user);
        Assertions.assertTrue(userRepository.findById(3L).orElseThrow().isBanned());
        mockMvc.perform(post("/control-room/unblock-user/{userId}", 3L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/control-room"));

        Assertions.assertFalse(userRepository.findById(3L).orElseThrow().isBanned());
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteUser() throws Exception {
        Assertions.assertTrue(userRepository.existsById(3L));
        mockMvc.perform(post("/control-room/delete-user/{userId}", 3L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/control-room"));

        Assertions.assertFalse(userRepository.existsById(3L));
    }
}