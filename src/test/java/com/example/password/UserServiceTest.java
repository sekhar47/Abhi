package com.example.password;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.entity.User;
import com.example.repository.Userrepo;

class UserServiceTest {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    @Mock
    private PasswordEncoder passwordEncoder;
    
	@Mock
    private Userrepo userRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testForgotPassword_ValidEmail() {
        // Arrange
        String email = "valid@example.com";
        User user = new User();
        user.setEmpemail(email);
        when(userRepository.findByEmpemail(email)).thenReturn(user);

        // Act
        String response = userService.forgotPassword(email);

        // Assert
        assertEquals("Reset link sent to your email.", response);
        assertNotNull(user.getToken());
        assertNotNull(user.getTokenCreationDate());
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    void testForgotPassword_InvalidEmail() {
        // Arrange
        String email = "invalid@example.com";
        when(userRepository.findByEmpemail(email)).thenReturn(null);

        // Act
        String response = userService.forgotPassword(email);

        // Assert
        assertEquals("Invalid email, please enter your email linked to your THBS account ", response);
        verify(userRepository, never()).save(any(User.class));
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testValidateResetToken_ValidToken() {
        // Arrange
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setToken(token);
        user.setTokenCreationDate(LocalDateTime.now());
        when(userRepository.findByToken(token)).thenReturn(user);

        // Act
        String response = userService.validateResetToken(token);

        // Assert
        assertEquals("Valid token", response);
    }

    @Test
    void testValidateResetToken_ExpiredToken() {
        // Arrange
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setToken(token);
        user.setTokenCreationDate(LocalDateTime.now().minusHours(1)); // Expired token
        when(userRepository.findByToken(token)).thenReturn(user);

        // Act
        String response = userService.validateResetToken(token);

        // Assert
        assertEquals("Token expired. please enter your email and reset the password again.", response);
    }

    @Test
    void testResetPassword_PasswordsMatch() {
        // Arrange
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setToken(token);
        user.setTokenCreationDate(LocalDateTime.now());
        String password = "newPassword";
        String confirmPassword = "newPassword";
        when(userRepository.findByToken(token)).thenReturn(user);

        // Act
        String response = userService.resetPassword(token, password, confirmPassword);

        // Assert
        assertEquals("Your password has been successfully updated.- TALENTRACK", response);
        assertNull(user.getToken());
        assertNull(user.getTokenCreationDate());
        verify(userRepository).save(user);
    }

    @Test
    void testResetPassword_PasswordsDoNotMatch() {
        // Arrange
        String token = UUID.randomUUID().toString();
        String password = "newPassword";
        String confirmPassword = "differentPassword";

        // Act
        String response = userService.resetPassword(token, password, confirmPassword);

        // Assert
        assertEquals("Passwords do not match. Recheck again", response);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testResetPassword_InvalidToken() {
        // Arrange
        String token = UUID.randomUUID().toString();
        when(userRepository.findByToken(token)).thenReturn(null);

        // Act
        String response = userService.resetPassword(token, "password", "password");

        // Assert
        assertEquals("Invalid token. please check your link, TRY CLICKING ON THE LINK AGAIN", response);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testResetPassword_ExpiredToken() {
        // Arrange
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setToken(token);
        user.setTokenCreationDate(LocalDateTime.now().minusHours(1)); // Expired token
        when(userRepository.findByToken(token)).thenReturn(user);

        // Act
        String response = userService.resetPassword(token, "password", "password");

        // Assert
        assertEquals("Token expired. please enter your email and reset the password again", response);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testInvalidEmailFormat() {
        String invalidEmail = "invalid-email-format";
        
        String result = userService.forgotPassword(invalidEmail);
        
        assertEquals("Invalid email, please enter your email linked to your THBS account".trim(), result.trim());
    }

 
    
    @Test
    void testTokenJustExpired() {
        // Create a token that has expired just a few milliseconds ago
        LocalDateTime tokenCreationDate = LocalDateTime.now().minusMinutes(EXPIRE_TOKEN_AFTER_MINUTES).minusSeconds(1);
        String token = UUID.randomUUID().toString();
        User user = new User();
        user.setToken(token);
        user.setTokenCreationDate(tokenCreationDate);
        when(userRepository.findByToken(token)).thenReturn(user);

        String result = userService.validateResetToken(token);
        
        assertEquals("Token expired. please enter your email and reset the password again.", result);
    }

}
