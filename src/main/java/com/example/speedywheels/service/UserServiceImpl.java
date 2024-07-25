package com.example.speedywheels.service;

import com.example.speedywheels.config.AdminConfig;
import com.example.speedywheels.model.dtos.UserProfileDTO;
import com.example.speedywheels.model.dtos.UserRegisterDTO;
import com.example.speedywheels.model.entity.Contact;
import com.example.speedywheels.model.entity.SocialMedia;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.UserRole;
import com.example.speedywheels.model.enums.Role;
import com.example.speedywheels.model.view.UserProfileView;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.service.interfaces.UserRoleService;
import com.example.speedywheels.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final AdminConfig adminConfig;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final UserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(AdminConfig adminConfig, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService, UserRoleService userRoleService) {
        this.adminConfig = adminConfig;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void saveUser(UserRegisterDTO userRegisterDTO) {
        User user = this.modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRegisteredOn(LocalDateTime.now());
        user.getRoles().add(this.userRoleService.findByRole(Role.USER));
        user.setProfilePictureUrl(this.cloudinaryService.saveImage(userRegisterDTO.getPicture()));
        user.setContact(new Contact()
                .setEmail(userRegisterDTO.getEmail()));
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void dbInitAdmin() {
        if (this.userRepository.count() == 0) {
            Set<UserRole> roles = new HashSet<>();
            roles.add(this.userRoleService.findByRole(Role.ADMIN));
            roles.add(this.userRoleService.findByRole(Role.USER));

            User user = new User()
                    .setUsername(adminConfig.getUsername())
                    .setPassword(passwordEncoder.encode(adminConfig.getPassword()))
                    .setFirstName(adminConfig.getFirstName())
                    .setLastName(adminConfig.getLastName())
                    .setContact(new Contact().setEmail(adminConfig.getEmail()).setPhone(adminConfig.getPhoneNumber()))
                    .setAge(adminConfig.getAge())
                    .setProfilePictureUrl(adminConfig.getProfilePicture())
                    .setCity(adminConfig.getCity())
                    .setRegisteredOn(LocalDateTime.now())
                    .setRoles(roles);
            this.userRepository.save(user);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void saveCurrentUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserProfileDTO mapUserToDTO(String username) {
        User user = this.userRepository.findByUsername(username).get();
        return this.modelMapper.map(user, UserProfileDTO.class);
    }

    @Override
    public UserProfileView mapUserToView(String username) {
        User user = this.userRepository.findByUsername(username).get();

        return this.modelMapper.map(user, UserProfileView.class);
    }

    @Override
    public void updateUser(UserProfileDTO userProfileDTO, String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userProfileDTO.getFirstName() != null) user.setFirstName(userProfileDTO.getFirstName());
        if (userProfileDTO.getLastName() != null) user.setLastName(userProfileDTO.getLastName());

        if (userProfileDTO.getContactPhone() != null) user.getContact().setPhone(userProfileDTO.getContactPhone());

        if (userProfileDTO.getCity() != null) user.setCity(userProfileDTO.getCity());

        SocialMedia socialMedia = user.getSocialMedias();
        if (socialMedia == null) {
            if (userProfileDTO.getSocialMediasGithub() != null ||
                    userProfileDTO.getSocialMediasTwitter() != null ||
                    userProfileDTO.getSocialMediasInstagram() != null ||
                    userProfileDTO.getSocialMediasFacebook() != null) {
                socialMedia = new SocialMedia();
                socialMedia.setUser(user);
                user.setSocialMedias(socialMedia);
            }
        }
        if (socialMedia != null) {
            if (userProfileDTO.getSocialMediasGithub() != null) socialMedia.setGithub(userProfileDTO.getSocialMediasGithub());
            if (userProfileDTO.getSocialMediasTwitter() != null) socialMedia.setTwitter(userProfileDTO.getSocialMediasTwitter());
            if (userProfileDTO.getSocialMediasInstagram() != null) socialMedia.setInstagram(userProfileDTO.getSocialMediasInstagram());
            if (userProfileDTO.getSocialMediasFacebook() != null) socialMedia.setFacebook(userProfileDTO.getSocialMediasFacebook());
        }

        System.out.println();
        this.userRepository.save(user);
    }

}
