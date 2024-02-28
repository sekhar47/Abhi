package com.example.emailverify;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.ui.Model;

import com.example.entity.User;
import com.example.repository.Userrepo;
import com.example.serviceauth.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserAccountControllerTest {

    @InjectMocks
    private UserAccountController userAccountController;

    @Mock
    private Userrepo userRepository;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    private EmailVerifyService emailService;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        // Set up any necessary dependencies or mocking before each test
    }

    @Test
    public void testDisplayRegistration() {
        Model model = mock(Model.class);
        User userEntity = new User();

        String result = userAccountController.displayRegistration(model, userEntity);

        assertEquals("register", result);
        verify(model).addAttribute("userEntity", userEntity);
    }

    @Test
    public void testRegisterUser() {
        Model model = mock(Model.class);
        User userEntity = new User();
        userEntity.setEmpemail("test@example.com");
        userEntity.setPassword("password");

        when(userRepository.findByEmpemail("test@example.com")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        String result = userAccountController.registerUser(model, userEntity);

        assertEquals("successfulRegistration", result);
        verify(userRepository).save(userEntity);
        verify(confirmationTokenRepository).save(any(ConfirmationToken.class));
        verify(emailService).sendEmail(any(SimpleMailMessage.class));
        verify(model).addAttribute("emailId", "test@example.com");
    }

    @Test
    public void testConfirmUserAccount() {
        Model model = mock(Model.class);

        // Create a valid token with a user
        User user = new User();
        user.setEmpemail("test@example.com");
        ConfirmationToken token = new ConfirmationToken(user);

        when(confirmationTokenRepository.findByConfirmationToken("validToken")).thenReturn(token);
        when(userRepository.findByEmpemail("test@example.com")).thenReturn(user);

        String result = userAccountController.confirmUserAccount(model, "validToken");

        assertEquals("accountVerified", result);
    }



    // Add more test cases as needed for your specific requirements
}
