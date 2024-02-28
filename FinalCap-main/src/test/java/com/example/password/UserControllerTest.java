package com.example.password;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testShowForgotPasswordForm() {
        String viewName = userController.showForgotPasswordForm();
        assertEquals("forgot-password", viewName);
    }

    @Test
    void testForgotPassword_ResetLinkSent() {
        // Arrange
        String email = "test@example.com";
        when(userService.forgotPassword(email)).thenReturn("Reset link sent to your email.");

        // Act
        String viewName = userController.forgotPassword(email, model);

        // Assert
        assertEquals("forgot-password", viewName);
        verify(model).addAttribute("message", "Reset link sent to your email.");
    }

    @Test
    void testForgotPassword_InvalidEmail() {
        // Arrange
        String email = "invalidemail@example.com";
        when(userService.forgotPassword(email)).thenReturn("Invalid email, please enter your email linked to your THBS account");

        // Act
        String viewName = userController.forgotPassword(email, model);

        // Assert
        assertEquals("forgot-password", viewName);
        verify(model).addAttribute("error", "Invalid email, please enter your email linked to your THBS account");
    }

    @Test
    void testShowResetPasswordForm_ValidToken() {
        // Arrange
        String token = "validToken";
        when(userService.validateResetToken(token)).thenReturn("Valid token");

        // Act
        String viewName = userController.showResetPasswordForm(token, model);

        // Assert
        assertEquals("reset-password", viewName);
        verify(model).addAttribute("token", token);
    }

    @Test
    void testShowResetPasswordForm_InvalidToken() {
        // Arrange
        String token = "invalidToken";
        when(userService.validateResetToken(token)).thenReturn("Invalid token");

        // Act
        String viewName = userController.showResetPasswordForm(token, model);

        // Assert
        assertEquals("error", viewName);
        verify(model).addAttribute("error", "Invalid token");
    }

    @Test
    void testResetPassword_Success() {
        // Arrange
        String token = "validToken";
        String password = "newPassword";
        String confirmPassword = "newPassword";
        when(userService.resetPassword(token, password, confirmPassword)).thenReturn("Your password has been successfully updated.");

        // Act
        String viewName = userController.resetPassword(token, password, confirmPassword, model);

        // Assert
        assertEquals("reset-password", viewName);
        verify(model).addAttribute("message", "Your password has been successfully updated.");
    }

    @Test
    void testResetPassword_PasswordsDoNotMatch() {
        // Arrange
        String token = "validToken";
        String password = "newPassword";
        String confirmPassword = "password123"; // Different password
        when(userService.resetPassword(token, password, confirmPassword)).thenReturn("Passwords do not match. Recheck again");

        // Act
        String viewName = userController.resetPassword(token, password, confirmPassword, model);

        // Assert
        assertEquals("reset-password", viewName);
        verify(model).addAttribute("error", "Passwords do not match. Recheck again");
    }

    @Test
    void testResetPassword_InvalidToken() {
        // Arrange
        String token = "invalidToken";
        String password = "newPassword";
        String confirmPassword = "newPassword";
        when(userService.resetPassword(token, password, confirmPassword)).thenReturn("Invalid token. please check your link, TRY CLICKING ON THE LINK AGAIN");

        // Act
        String viewName = userController.resetPassword(token, password, confirmPassword, model);

        // Assert
        assertEquals("reset-password", viewName);
        verify(model).addAttribute("error", "Invalid token. please check your link, TRY CLICKING ON THE LINK AGAIN");
    }}
