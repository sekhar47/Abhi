package com.example.logincontroller;

import com.example.dto.UserDto;
import com.example.serviceauth.UserService;
import com.example.skillvalidate.EmployeeSkillValidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class LoginControllerTest {

    @Mock
    private UserDetailsService userDetailsServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private EmployeeSkillValidateService employeeSkillValidateServiceMock;

    @Mock
    private Model modelMock;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getRegistrationPage_ReturnsLandingPage() {
        // Act
        String viewName = loginController.getRegistrationPage(new UserDto());

        // Assert
        assertEquals("landing", viewName);
    }

    @Test
    void login_ReturnsLoginPageWithError() {
        // Arrange
        String error = "Invalid credentials";

        // Act
        String viewName = loginController.login(error, modelMock);

        // Assert
        assertEquals("login", viewName);
        verify(modelMock).addAttribute("errorMessage", "Incorrect username or password. Please try again.");
    }

    @Test
    void userPage_ReturnsUserDashboardPage() {
        // Arrange
        Principal principal = () -> "user";
        UserDetails userDetails = new User("user", "password", new ArrayList<>());
        when(userDetailsServiceMock.loadUserByUsername("user")).thenReturn(userDetails);
        List<String> notifications = new ArrayList<>();
        when(employeeSkillValidateServiceMock.getStoredNotifications()).thenReturn(notifications);

        // Act
        String viewName = loginController.userPage(modelMock, principal);

        // Assert
        assertEquals("userdashboard", viewName);
        verify(modelMock).addAttribute("user", userDetails);
        verify(modelMock).addAttribute("notifications", notifications);
    }

    @Test
    void adminPage_ReturnsAdminPage() {
        // Arrange
        Principal principal = () -> "admin";
        UserDetails userDetails = new User("admin", "password", new ArrayList<>());
        when(userDetailsServiceMock.loadUserByUsername("admin")).thenReturn(userDetails);

        // Act
        String viewName = loginController.adminPage(modelMock, principal);

        // Assert
        assertEquals("admin", viewName);
        verify(modelMock).addAttribute("user", userDetails);
    }

   }
