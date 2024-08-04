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
import com.example.speedywheels.service.interfaces.UserService;
import com.example.speedywheels.util.FavoriteUtils;
import com.example.speedywheels.util.ModelAttributeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
            roles.add(this.userRoleService.findByRole(Role.USER));
            roles.add(this.userRoleService.findByRole(Role.ADMIN));

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
                    .setRoles(roles)
                    .setSocialMedias(new SocialMedia()
                            .setTwitter("https://twitter.com/adminUser")
                            .setGithub("https://github.com/adminUser")
                            .setInstagram("https://instagram.com/adminUser")
                            .setFacebook("https://facebook.com/adminUser"));
            user.getSocialMedias().setUser(user);
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

        this.userRepository.save(user);
    }

    @Override
    public List<UserControlRoomView> findAllUsersExcludingVenci777() {
        return userRepository.findAllUsersExcludingUsername("venci777")
                .stream()
                .map(user -> {
                    UserControlRoomView view = modelMapper.map(user, UserControlRoomView.class);
                    view.setRegisteredOn(ModelAttributeUtil.formatDate(user.getRegisteredOn()));
                    view.setVehicleCounts(user.getMyCars().size() + user.getMyMotorcycles().size());
                    return view;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void addAdminRole(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserRole adminRole = this.userRoleService.findByRole(Role.ADMIN);
            user.getRoles().add(adminRole);
            this.userRepository.save(user);
        }
    }

    @Override
    public void removeAdminRole(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserRole adminRole = this.userRoleService.findByRole(Role.ADMIN);
            user.getRoles().remove(adminRole);
            this.userRepository.save(user);
        }
    }

    @Override
    public void blockUser(Long userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.setBanned(true);
                    userRepository.save(user);
                });
    }

    @Override
    public void unblockUser(Long userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.setBanned(false);
                    userRepository.save(user);
                });
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    user.getMyCars().forEach(car -> FavoriteUtils.removeCarFromFavorites(this,car));
                    user.getMyMotorcycles().forEach(motorcycle -> FavoriteUtils.removeMotorcycleFromFavorites(this,motorcycle));
                    userRepository.deleteById(userId);
                });
    }

    @Override
    public List<UserEmailDTO> getAllUserEmails() {
        return this.userRepository.findAll().stream()
                .map(user -> this.modelMapper.map(user, UserEmailDTO.class))
                .collect(Collectors.toList());
    }
}
