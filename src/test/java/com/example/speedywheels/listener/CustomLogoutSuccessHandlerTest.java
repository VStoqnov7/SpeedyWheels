package com.example.speedywheels.listener;

import com.example.speedywheels.model.entity.User;
import com.example.speedywheels.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomLogoutSuccessHandlerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setActive(true);
    }

    @Test
    void testOnLogoutSuccess() throws IOException, ServletException {
        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        logoutSuccessHandler.onLogoutSuccess(request, response, authentication);

        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();
        assertFalse(capturedUser.isActive());
        verify(response).sendRedirect("/");
    }

    @Test
    void testOnLogoutSuccessUserNotFound() throws IOException, ServletException {
        when(authentication.getName()).thenReturn("nonExistentUser");
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        logoutSuccessHandler.onLogoutSuccess(request, response, authentication);

        verify(userRepository, never()).save(any(User.class));
        verify(response).sendRedirect("/");
    }

    @Test
    void testOnLogoutSuccessNoAuthentication() throws IOException, ServletException {
        logoutSuccessHandler.onLogoutSuccess(request, response, null);

        verify(userRepository, never()).save(any(User.class));
        verify(response).sendRedirect("/");
    }
}