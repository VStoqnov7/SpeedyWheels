package com.example.speedywheels.service;

import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.model.entity.UserRole;
import com.example.speedywheels.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private UserDetails map(User user) {
        if (user.isBanned()) {
            throw new UsernameNotFoundException("User is banned.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(this::mapRole).collect(Collectors.toList())
        );
    }

    private GrantedAuthority mapRole(UserRole userRoleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getName().name());
    }

}
