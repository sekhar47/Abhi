package com.example.userprofile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import com.example.entity.User;
import com.example.repository.Userrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserProfileServiceImplTest {

    @Mock
    private Userrepo userRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserByEmpid() {
        // Mocking
        when(userRepository.findByEmpid("123")).thenReturn(new User("123", "John", null, null, null, null, null, null, null, null, null));

        // Test
        User result = userProfileService.getUserByEmpid("123");

        // Verify
        assertNotNull(result);
        assertEquals("123", result.getEmpid());
        assertEquals("John", result.getName());
    }

    @Test
    void testGetUserByEmail() {
        // Mocking
        when(userRepository.findByEmpemail("john@example.com")).thenReturn(new User("123", "John", null, null, null, null, null, null, null, null, null));

        // Test
        User result = userProfileService.getUserByEmail("john@example.com");

        // Verify
        assertNotNull(result);
        assertEquals("123", result.getEmpid());
        assertEquals("John", result.getName());
    }

    @Test
    void testUpdateUserProfile() {
        // Mocking
        User user = new User("123", "John", null, null, null, null, null, null, null, null, null);
        when(userRepository.save(user)).thenReturn(user);

        // Test
        User result = userProfileService.updateUserProfile(user);

        // Verify
        assertNotNull(result);
        assertEquals("123", result.getEmpid());
        assertEquals("John", result.getName());
    }

}
