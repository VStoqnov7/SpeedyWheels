package com.example.speedywheels.service;

import com.example.speedywheels.model.entity.UserRole;
import com.example.speedywheels.model.enums.Role;
import com.example.speedywheels.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserRoleServiceImplTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleServiceImpl userRoleServiceImpl;

    @Test
    void dbInitWhenNoRolesPresent() {
        when(userRoleRepository.count()).thenReturn(0L);

        userRoleServiceImpl.dbInit();

        ArgumentCaptor<List<UserRole>> rolesCaptor = ArgumentCaptor.forClass(List.class);
        verify(userRoleRepository).saveAllAndFlush(rolesCaptor.capture());
        List<UserRole> savedRoles = rolesCaptor.getValue();
        assertEquals(2, savedRoles.size());
        assertTrue(savedRoles.stream().anyMatch(role -> role.getName() == Role.USER));
        assertTrue(savedRoles.stream().anyMatch(role -> role.getName() == Role.ADMIN));
    }

    @Test
    void dbInitWhenRolesAlreadyPresent() {
        when(userRoleRepository.count()).thenReturn(2L);

        userRoleServiceImpl.dbInit();

        verify(userRoleRepository, never()).saveAllAndFlush(any());
    }

    @Test
    void findByRole() {
        UserRole adminRole = new UserRole(Role.ADMIN);
        when(userRoleRepository.findByName(Role.ADMIN)).thenReturn(adminRole);

        UserRole result = userRoleServiceImpl.findByRole(Role.ADMIN);

        assertEquals(Role.ADMIN, result.getName());
        verify(userRoleRepository).findByName(Role.ADMIN);
    }
}