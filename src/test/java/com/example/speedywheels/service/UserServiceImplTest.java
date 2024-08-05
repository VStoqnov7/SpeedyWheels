package com.example.speedywheels.service;

import com.example.speedywheels.config.AdminConfig;
import com.example.speedywheels.model.dtos.UserEmailDTO;
import com.example.speedywheels.model.dtos.UserProfileDTO;
import com.example.speedywheels.model.dtos.UserRegisterDTO;
import com.example.speedywheels.model.entity.*;
import com.example.speedywheels.model.enums.Role;
import com.example.speedywheels.model.view.UserControlRoomView;
import com.example.speedywheels.model.view.UserProfileView;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.service.interfaces.UserRoleService;
import com.example.speedywheels.util.FavoriteUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private UserRoleService userRoleService;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private UserRegisterDTO userRegisterDTO;
    private UserProfileDTO userProfileDTO;
    private User user;
    private UserRole adminRole;
    private UserRole userRole;
    private MultipartFile picture;

    @BeforeEach
    void setUp() {
        userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setFirstName("John");
        userRegisterDTO.setLastName("Doe");
        userRegisterDTO.setAge(25);
        picture = mock(MultipartFile.class);
        userRegisterDTO.setPicture(picture);

        userProfileDTO = new UserProfileDTO();
        userProfileDTO.setFirstName("John");
        userProfileDTO.setLastName("Doe");
        userProfileDTO.setContactPhone("123456789");
        userProfileDTO.setCity("City");
        userProfileDTO.setSocialMediasGithub("github.com/test");

        user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setRegisteredOn(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setContact(new Contact());

        adminRole = new UserRole(Role.ADMIN);

        userRole = new UserRole(Role.USER);
    }

    @Test
    void saveUser() {
        when(modelMapper.map(userRegisterDTO, User.class)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(cloudinaryService.saveImage(picture)).thenReturn("uploadedPictureUrl");
        when(userRoleService.findByRole(Role.USER)).thenReturn(userRole);

        userService.saveUser(userRegisterDTO);

        verify(userRepository).saveAndFlush(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("uploadedPictureUrl", savedUser.getProfilePictureUrl());
        assertEquals("test@example.com", savedUser.getContact().getEmail());
        assertTrue(savedUser.getRoles().contains(userRole));
    }

    @Test
    void dbInitAdmin() {
        when(adminConfig.getUsername()).thenReturn("admin");
        when(adminConfig.getPassword()).thenReturn("adminPass");
        when(adminConfig.getFirstName()).thenReturn("Admin");
        when(adminConfig.getLastName()).thenReturn("User");
        when(adminConfig.getEmail()).thenReturn("admin@example.com");
        when(adminConfig.getPhoneNumber()).thenReturn("123456789");
        when(adminConfig.getAge()).thenReturn(30);
        when(adminConfig.getProfilePicture()).thenReturn("adminPictureUrl");
        when(adminConfig.getCity()).thenReturn("AdminCity");
        when(userRepository.count()).thenReturn(0L);
        when(userRoleService.findByRole(Role.ADMIN)).thenReturn(adminRole);
        when(userRoleService.findByRole(Role.USER)).thenReturn(userRole);
        when(passwordEncoder.encode("adminPass")).thenReturn("adminPass");

        userService.dbInitAdmin();

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("admin", savedUser.getUsername());
        assertTrue(savedUser.getRoles().contains(adminRole));
        assertTrue(savedUser.getRoles().contains(userRole));
        assertEquals("adminPass", savedUser.getPassword());
        assertEquals("Admin", savedUser.getFirstName());
        assertEquals("User", savedUser.getLastName());
        assertEquals("admin@example.com", savedUser.getContact().getEmail());
        assertEquals("123456789", savedUser.getContact().getPhone());
        assertEquals(30, savedUser.getAge());
        assertEquals("AdminCity", savedUser.getCity());
        assertEquals("adminPictureUrl", savedUser.getProfilePictureUrl());
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("testUser");

        assertTrue(result.isPresent());
        assertEquals("testUser", result.get().getUsername());
    }

    @Test
    void saveCurrentUser() {
        userService.saveCurrentUser(user);

        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertEquals(user.getUsername(), capturedUser.getUsername());
    }

    @Test
    void findAll() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
    }

    @Test
    void mapUserToDTO() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        when(modelMapper.map(user, UserProfileDTO.class)).thenReturn(userProfileDTO);

        UserProfileDTO result = userService.mapUserToDTO("testUser");

        assertNotNull(result);
        assertEquals(userProfileDTO, result);
    }

    @Test
    void mapUserToView() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        UserProfileView userProfileView = new UserProfileView();
        when(modelMapper.map(user, UserProfileView.class)).thenReturn(userProfileView);

        UserProfileView result = userService.mapUserToView("testUser");

        assertNotNull(result);
        assertEquals(userProfileView, result);
    }

    @Test
    void updateUser() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        UserProfileDTO updateDTO = new UserProfileDTO();
        updateDTO.setFirstName("Jane");
        updateDTO.setContactPhone("987654321");
        updateDTO.setCity("NewCity");
        updateDTO.setSocialMediasGithub("newGithub.com/test");

        userService.updateUser(updateDTO, "testUser");

        verify(userRepository).save(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals("Jane", updatedUser.getFirstName());
        assertEquals("987654321", updatedUser.getContact().getPhone());
        assertEquals("NewCity", updatedUser.getCity());
        assertEquals("newGithub.com/test", updatedUser.getSocialMedias().getGithub());
    }

    @Test
    void findAllUsersAndAdminsExceptConfiguredAdmin() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setRegisteredOn(LocalDateTime.now().minusDays(1));
        User user2 = new User();
        user2.setUsername(adminConfig.getUsername());
        user2.setRegisteredOn(LocalDateTime.now());

        when(userRepository.findAllUsersExcludingUsername(adminConfig.getUsername())).thenReturn(Arrays.asList(user1));
        when(modelMapper.map(user1, UserControlRoomView.class)).thenReturn(new UserControlRoomView());

        List<UserControlRoomView> result = userService.findAllUsersAndAdminsExceptConfiguredAdmin();

        assertEquals(1, result.size());
    }

    @Test
    void addAdminRole() {
        User user = new User();
        UserRole adminRole = new UserRole(Role.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRoleService.findByRole(Role.ADMIN)).thenReturn(adminRole);

        userService.addAdminRole(1L);

        verify(userRepository).findById(1L);
        verify(userRoleService).findByRole(Role.ADMIN);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertTrue(savedUser.getRoles().contains(adminRole));
    }

    @Test
    void removeAdminRole() {
        User user = new User();
        UserRole adminRole = new UserRole(Role.ADMIN);
        user.getRoles().add(adminRole);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRoleService.findByRole(Role.ADMIN)).thenReturn(adminRole);

        userService.removeAdminRole(1L);

        verify(userRepository).findById(1L);
        verify(userRoleService).findByRole(Role.ADMIN);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertFalse(savedUser.getRoles().contains(adminRole));
    }

    @Test
    void blockUser() {
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.blockUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertTrue(savedUser.isBanned());
    }

    @Test
    void unblockUser() {
        User user = new User();
        user.setBanned(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.unblockUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertFalse(savedUser.isBanned());
    }

    @Test
    void deleteUser() {
        Car car = new Car();
        Motorcycle motorcycle = new Motorcycle();

        user.setMyCars(Collections.singletonList(car));
        user.setMyMotorcycles(Collections.singletonList(motorcycle));
        user.setFavoriteCars(Collections.singletonList(new UserFavoriteCars().setCar(car)));
        user.setFavoriteMotorcycles(Collections.singletonList(new UserFavoriteMotorcycle().setMotorcycle(motorcycle)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        try (MockedStatic<FavoriteUtils> utilities = mockStatic(FavoriteUtils.class)) {
            userService.deleteUser(1L);
            utilities.verify(() -> FavoriteUtils.removeCarFromFavorites(userService, car));
            utilities.verify(() -> FavoriteUtils.removeMotorcycleFromFavorites(userService, motorcycle));
            verify(userRepository).findById(1L);
            verify(userRepository).deleteById(1L);
            verifyNoMoreInteractions(userRepository);
        }
    }

    @Test
    void getAllUserEmails() {
        user.getContact().setEmail("test@example.com");

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(modelMapper.map(user, UserEmailDTO.class)).thenReturn(new UserEmailDTO().setContactEmail("test@example.com"));

        List<UserEmailDTO> result = userService.getAllUserEmails();

        verify(userRepository).findAll();
        verify(modelMapper).map(user, UserEmailDTO.class);

        assertEquals(1, result.size());
        assertEquals("test@example.com", result.get(0).getContactEmail());
    }
}