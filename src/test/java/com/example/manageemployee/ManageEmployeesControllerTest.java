package com.example.manageemployee;

import com.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ManageEmployeesControllerTest {

    @Mock
    private Manageempservice manageempServiceMock;

    @Mock
    private Model modelMock;

    @InjectMocks
    private ManageEmployeesController manageEmployeesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testDeleteEmployee() {
        // Act
        String viewName = manageEmployeesController.deleteEmployee(modelMock);

        // Assert
        assertEquals("manage_employees", viewName);
    }

    @Test
    void testManageEmployees() {
        // Arrange
        List<User> expectedUsers = new ArrayList<>();
        when(manageempServiceMock.getAllUsers()).thenReturn(expectedUsers);

        // Act
        String viewName = manageEmployeesController.manageEmployees(modelMock);

        // Assert
        assertEquals("deleteemployee", viewName);
        verify(modelMock).addAttribute("users", expectedUsers);
    }

    @Test
    void testDeleteEmployees() {
        // Arrange
        List<String> empIds = new ArrayList<>();
        empIds.add("emp1");
        empIds.add("emp2");

        // Act
        String viewName = manageEmployeesController.deleteEmployees(empIds);

        // Assert
        assertEquals("redirect:/deleteEmployees", viewName);
        verify(manageempServiceMock).deleteUsersByEmpIds(empIds);
    }

    @Test
    void testEditPrivilege() {
        // Arrange
        String empId = "emp123";
        User user = new User();
        when(manageempServiceMock.getUserByEmpid(empId)).thenReturn(user);

        // Act
        String viewName = manageEmployeesController.editPrivilege(empId, modelMock);

        // Assert
        assertEquals("edit_privilege", viewName);
        verify(modelMock).addAttribute("user", user);
    }

    @Test
    void testUpdatePrivilege() {
        // Arrange
        String empId = "emp123";
        String privilege = "ADMIN";

        // Act
        String viewName = manageEmployeesController.updatePrivilege(empId, privilege);

        // Assert
        assertEquals("redirect:/home", viewName);
        verify(manageempServiceMock).updateUserPrivilege(empId, privilege);
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        List<User> expectedEmployees = new ArrayList<>();
        when(manageempServiceMock.getAllUsers()).thenReturn(expectedEmployees);

        // Act
        String viewName = manageEmployeesController.getAllEmployees(modelMock);

        // Assert
        assertEquals("privilage", viewName);
        verify(modelMock).addAttribute("employees", expectedEmployees);
    }

    @Test
    void testUpdatePrivilegeForEmployee() {
        // Arrange
        String empId = "emp123";

        // Act
        String viewName = manageEmployeesController.updatePrivilege(empId);

        // Assert
        assertEquals("redirect:/privilage", viewName);
        verify(manageempServiceMock).updatePrivilege(empId);
    }
}
