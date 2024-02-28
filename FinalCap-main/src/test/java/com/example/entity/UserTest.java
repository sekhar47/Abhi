package com.example.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testConstructor() {
        // Given
        String empid = "123";
        String name = "John Doe";
        String empemail = "john@example.com";
        String password = "password123";
        String empmobile = "1234567890";
        Boolean availability = true;
        String privilage = "USER";
        String designation = "Developer";
        String token = "token123";
        LocalDateTime tokenCreationDate = LocalDateTime.now();
        Set<EmployeeSkill> employeeSkills = new HashSet<>();

        // When
        User newUser = new User(empid, name, empemail, password, empmobile, availability, privilage, designation,
                token, tokenCreationDate, employeeSkills);

        // Then
        assertNotNull(newUser);
        assertEquals(empid, newUser.getEmpid());
        assertEquals(name, newUser.getName());
        assertEquals(empemail, newUser.getEmpemail());
        assertEquals(password, newUser.getPassword());
        assertEquals(empmobile, newUser.getEmpmobile());
        assertEquals(availability, newUser.getAvailability());
        assertEquals(privilage, newUser.getPrivilage());
        assertEquals(designation, newUser.getDesignation());
        assertEquals(token, newUser.getToken());
        assertEquals(tokenCreationDate, newUser.getTokenCreationDate());
        assertEquals(employeeSkills, newUser.getEmployeeSkills());
    }

    @Test
    public void testGetterAndSetter() {
        // Given
        String empid = "123";
        String name = "John Doe";
        String empemail = "john@example.com";
        String password = "password123";
        String empmobile = "1234567890";
        Boolean availability = true;
        String privilage = "USER";
        String designation = "Developer";
        String token = "token123";
        LocalDateTime tokenCreationDate = LocalDateTime.now();
        byte[] profilePicture = new byte[] { 0x00, 0x01, 0x02 };
        String imageName = "image.jpg";
        Set<EmployeeSkill> employeeSkills = new HashSet<>();

        // When
        user.setEmpid(empid);
        user.setName(name);
        user.setEmpemail(empemail);
        user.setPassword(password);
        user.setEmpmobile(empmobile);
        user.setAvailability(availability);
        user.setPrivilage(privilage);
        user.setDesignation(designation);
        user.setToken(token);
        user.setTokenCreationDate(tokenCreationDate);
        user.setProfilePicture(profilePicture);
        user.setImageName(imageName);
        user.setEmployeeSkills(employeeSkills);

        // Then
        assertEquals(empid, user.getEmpid());
        assertEquals(name, user.getName());
        assertEquals(empemail, user.getEmpemail());
        assertEquals(password, user.getPassword());
        assertEquals(empmobile, user.getEmpmobile());
        assertEquals(availability, user.getAvailability());
        assertEquals(privilage, user.getPrivilage());
        assertEquals(designation, user.getDesignation());
        assertEquals(token, user.getToken());
        assertEquals(tokenCreationDate, user.getTokenCreationDate());
        assertEquals(profilePicture, user.getProfilePicture());
        assertEquals(imageName, user.getImageName());
        assertEquals(employeeSkills, user.getEmployeeSkills());
    }

    @Test
    public void testAvailabilityDefaultValue() {
        // Test that the default value of availability is false
        User newUser = new User();
        assertFalse(newUser.getAvailability());
    }

    @Test
    public void testSetAndGetEnabled() {
        // Test setEnabled and isEnabled methods
        user.setEnabled(true);
        assertTrue(user.isEnabled());

        user.setEnabled(false);
        assertFalse(user.isEnabled());
    }

    @Test
    public void testDefaultPrivilege() {
        // Test that the default privilege is set to "USER"
        assertEquals("USER", user.getPrivilage());
    }
    
    
    @Test
    public void testGetAvailabilityWithNull() {
        // Given
        user.setAvailability(null);

        // When
        boolean availability = user.getAvailability();

        // Then
        assertFalse(availability);
    }

 

    @Test
    public void testSetAndGetProfilePicture() {
        // Given
        byte[] profilePicture = new byte[] { 0x00, 0x01, 0x02 };

        // When
        user.setProfilePicture(profilePicture);

        // Then
        assertArrayEquals(profilePicture, user.getProfilePicture());
    }

    @Test
    public void testSetAndGetEmployeeSkills() {
        // Given
        Set<EmployeeSkill> employeeSkills = new HashSet<>();
        EmployeeSkill skill1 = new EmployeeSkill();
        EmployeeSkill skill2 = new EmployeeSkill();
        employeeSkills.add(skill1);
        employeeSkills.add(skill2);

        // When
        user.setEmployeeSkills(employeeSkills);

        // Then
        assertEquals(employeeSkills, user.getEmployeeSkills());
    }

 
    @Test
    public void testTokenDefault() {
        // Test that the default value of token is null
        assertNull(user.getToken());
    }

   
}
