//package com.example.logincontroller.repository;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import com.example.entity.User;
//import com.example.repository.Userrepo;
//
//@DataJpaTest
//public class UserrepoTest {
//
//    @Autowired
//    private Userrepo userRepo;
//
//    @Test
//    public void testFindByEmpemail() {
//        // Given
//        String email = "test@example.com";
//        
//        // When
//        User user = userRepo.findByEmpemail(email);
//        
//        // Then
//        assertNotNull(user);
//        assertEquals(email, user.getEmpemail());
//    }
//
//    @Test
//    public void testFindByEmpid() {
//        // Given
//        String empId = "123";
//        
//        // When
//        User user = userRepo.findByEmpid(empId);
//        
//        // Then
//        assertNotNull(user);
//        assertEquals(empId, user.getEmpid());
//    }
//
//    @Test
//    public void testFindByName() {
//        // Given
//        String name = "rajkumar";
//        
//        // When
//        Optional<User> userOptional = userRepo.findByName(name);
//        
//        // Then
//        assertTrue(userOptional.isPresent());
//        assertEquals(name, userOptional.get().getName());
//    }
//
//    @Test
//    public void testFindByPrivilage() {
//        // Given
//        String privilege = "admin";
//        
//        // When
//        List<User> users = userRepo.findByPrivilage(privilege);
//        
//        // Then
//        assertFalse(users.isEmpty());
//        users.forEach(user -> assertEquals(privilege, user.getPrivilage()));
//    }
//
//    @Test
//    public void testFindByToken() {
//        // Given
//        String token = "abc123";
//        
//        // When
//        User user = userRepo.findByToken(token);
//        
//        // Then
//        assertNotNull(user);
//        assertEquals(token, user.getToken());
//    }
//
//    @Test
//    public void testFindByUsername() {
//        // Given
//        String username = "johndoe";
//        
//        // When
//        User user = userRepo.findByUsername(username);
//        
//        // Then
//        assertNotNull(user);
//        assertEquals(username, user.getName());
//    }
//}
