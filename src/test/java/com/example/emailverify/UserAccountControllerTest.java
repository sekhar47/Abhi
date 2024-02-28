package com.example.emailverify;
 
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
 
import com.example.entity.User;
import com.example.repository.Userrepo;
import com.example.serviceauth.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
 
@RunWith(MockitoJUnitRunner.class)
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
 
    @Before
    public void setup() {
        // Set up any necessary dependencies or mocking before each test
    }
 
    @Test
    public void testDisplayRegistration() {
        Model model = mock(Model.class);
        User userEntity = new User();
 
        String result = userAccountController.displayRegistration(model, userEntity);
 
        assertEquals("register", result);
        verify(model).addAttribute(eq("userEntity"), any(User.class));
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
        verify(model).addAttribute(eq("emailId"), eq("test@example.com"));
    }
 
    @Test
    public void testConfirmUserAccount() {
        Model model = mock(Model.class);
 
        when(confirmationTokenRepository.findByConfirmationToken("validToken")).thenReturn(new ConfirmationToken());
        when(userRepository.findByEmpemail(anyString())).thenReturn(new User());
 
        String result = userAccountController.confirmUserAccount(model, "validToken");
 
        assertEquals("accountVerified", result);
    }
 
    // Add more test cases as needed for your specific requirements
}