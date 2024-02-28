package com.example.manageemployee;

import com.example.entity.User;
import com.example.repository.Userrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ManageempServiceImplTest {

    @Mock
    private Userrepo userrepoMock;

    @InjectMocks
    private ManageempServiceImpl manageempService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        when(userrepoMock.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = manageempService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testDeleteUsersByEmpIds() {
        // Arrange
        List<String> empIds = new ArrayList<>();
        empIds.add("emp1");
        empIds.add("emp2");

        // Act
        manageempService.deleteUsersByEmpIds(empIds);

        // Assert
        verify(userrepoMock, times(2)).deleteById(anyString());
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User();
        when(userrepoMock.findByEmpemail(email)).thenReturn(expectedUser);

        // Act
        User actualUser = manageempService.getUserByEmail(email);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testUpdateUserPrivilege() {
        // Arrange
        String empId = "emp123";
        String privilege = "ADMIN";
        User user = new User();
        when(userrepoMock.findByEmpid(empId)).thenReturn(user);

        // Act
        manageempService.updateUserPrivilege(empId, privilege);

        // Assert
        assertEquals(privilege, user.getPrivilage());
        verify(userrepoMock).save(user);
    }

    @Test
    void testGetUserByEmpid() {
        // Arrange
        String empId = "emp123";
        User expectedUser = new User();
        when(userrepoMock.findByEmpid(empId)).thenReturn(expectedUser);

        // Act
        User actualUser = manageempService.getUserByEmpid(empId);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testUpdatePrivilege() {
        // Arrange
        String empId = "emp123";
        User user = new User();
        when(userrepoMock.findByEmpid(empId)).thenReturn(user);

        // Act
        manageempService.updatePrivilege(empId);

        // Assert
        assertEquals("ADMIN", user.getPrivilage());
        verify(userrepoMock).save(user);
    }
}
