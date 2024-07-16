package com.example.speedywheels.service;

import com.example.speedywheels.model.dtos.UserRegisterDTO;
import com.example.speedywheels.model.entity.Contact;
import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.UserRole;
import com.example.speedywheels.model.enums.Role;
import com.example.speedywheels.repository.UserRepository;
import com.example.speedywheels.service.interfaces.UserRoleService;
import com.example.speedywheels.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryService cloudinaryService;
    private final UserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CloudinaryService cloudinaryService, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryService = cloudinaryService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void saveUser(UserRegisterDTO userRegisterDTO) {
        User user = this.modelMapper.map(userRegisterDTO,User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRegisteredOn(LocalDateTime.now());
        user.getRoles().add(this.userRoleService.findByRole(Role.USER));
        user.setProfilePictureUrl(this.cloudinaryService.saveImage(userRegisterDTO.getPicture()));
        user.setContact(new Contact()
                .setEmail(userRegisterDTO.getEmail()));
        this.userRepository.saveAndFlush(user);
    }
}
