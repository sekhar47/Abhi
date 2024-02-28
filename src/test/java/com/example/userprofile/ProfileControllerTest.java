package com.example.userprofile;

import com.example.entity.User;
import com.example.repository.Userrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private Userrepo userRepository;

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowProfile() {
        // Mock data
        User mockUser = new User();
        mockUser.setEmpid("testEmpId");

        when(principal.getName()).thenReturn("testEmpId");
        when(userProfileService.getUserByEmpid("testEmpId")).thenReturn(mockUser);

        // Call the method
        String result = profileController.showProfile(model, principal);

        // Verify interactions and assertions
        verify(model).addAttribute("user", mockUser);
        assertEquals("profile", result);
    }

    @Test
    void testEditAvailability() {
        // Mock data
        User mockUser = new User();
        mockUser.setEmpid("testEmpId");

        when(principal.getName()).thenReturn("testEmpId");
        when(userProfileService.getUserByEmpid("testEmpId")).thenReturn(mockUser);

        // Call the method
        String result = profileController.editAvailability(model, principal);

        // Verify interactions and assertions
        verify(model).addAttribute("user", mockUser);
        assertEquals("editAvailability", result);
    }

    @Test
    void testUpdateProfile() {
        // Mock data
        User mockUpdatedUser = new User();
        mockUpdatedUser.setEmpid("testEmpId");
        mockUpdatedUser.setEmpmobile("123456789");
        mockUpdatedUser.setAvailability(true);
        mockUpdatedUser.setDesignation("Software Engineer");

        User mockCurrentUser = Mockito.spy(new User());
        mockCurrentUser.setEmpid("testEmpId");

        when(principal.getName()).thenReturn("testEmpId");
        when(userProfileService.getUserByEmpid("testEmpId")).thenReturn(mockCurrentUser);

        // Call the method
        String result = profileController.updateProfile(mockUpdatedUser, principal);

        // Verify interactions and assertions
        Mockito.verify(mockCurrentUser).setEmpmobile("123456789");
        Mockito.verify(mockCurrentUser).setAvailability(true);
        Mockito.verify(mockCurrentUser).setDesignation("Software Engineer");
        Mockito.verify(userProfileService).updateUserProfile(mockCurrentUser);
        assertEquals("redirect:/profile", result);
    }
}
