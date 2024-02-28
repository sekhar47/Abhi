package com.example.controller;
import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmployeeSkill;
import com.example.entity.Skills;
import com.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private EmployeeService employeeServiceMock;

    @Mock
    private Model modelMock;

    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        adminController = new AdminController(employeeServiceMock);
    }

    @Test
    void getAllEmployeeDetails_ReturnsEmployeeDetailsList() {
        // Arrange
        List<EmployeeDetailsDTO> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new EmployeeDetailsDTO(1, "123", "John Doe", "john@example.com", "1234567890", "Java Developer", "IT", "Programming", "Expert", "Oracle Certified Java Programmer", true, true, 30, "password123", "ADMIN", new EmployeeSkill(), new Skills()));
        when(employeeServiceMock.getAllEmployeeDetails()).thenReturn(expectedEmployees);

        // Act
        List<EmployeeDetailsDTO> actualEmployees = adminController.getAllEmployeeDetails();

        // Assert
        assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void showEmployeeDetails_ReturnsEmployeesPage() {
        // Arrange
        List<EmployeeDetailsDTO> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new EmployeeDetailsDTO(2, "456", "Jane Smith", "jane@example.com", "9876543210", "Data Analyst", "Analytics", "Data Processing", "Intermediate", "Data Analyst Certification", true, true, 30, "password456", "USER", new EmployeeSkill(), new Skills()));
        when(employeeServiceMock.getAllEmployeeDetails()).thenReturn(expectedEmployees);

        // Act
        String viewName = adminController.showEmployeeDetails(modelMock);

        // Assert
        assertEquals("employees", viewName);
        verify(modelMock).addAttribute("employees", expectedEmployees);
    }


    @Test
    void searchEmployees_ReturnsEmployeesPage() {
        // Arrange
        List<EmployeeDetailsDTO> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new EmployeeDetailsDTO(3, "789", "Alice", "alice@example.com", "1231231234", "Java Developer", "IT", "Programming", "Expert", "Oracle Certified Java Programmer", true, true, 30, "password789", "ADMIN", new EmployeeSkill(), new Skills()));
        expectedEmployees.add(new EmployeeDetailsDTO(4, "890", "Bob", "bob@example.com", "4564564567", "Tester", "IT", "Testing", "Intermediate", "ISTQB Certification", true, true, 30, "password890", "USER", new EmployeeSkill(), new Skills()));
        when(employeeServiceMock.searchEmployees(any(), any(), any(), any(), any())).thenReturn(expectedEmployees);

        // Act
        String viewName = adminController.searchEmployees("123", "Java", "IT", "Programming", "Expert", modelMock);

        // Assert
        assertEquals("employees", viewName);
        verify(modelMock).addAttribute("employees", expectedEmployees);
    }

    @Test
    void showAdminDashboard_ReturnsAdminPage() {
        // Act
        String viewName = adminController.showAdminDashboard(modelMock);

        // Assert
        assertEquals("admin", viewName);
    }

    @Test
    void reviewSkill_ReturnsOkResponse() {
        // Arrange
        String empId = "123";
        Integer skillId = 456;

        // Act
        ResponseEntity<?> responseEntity = adminController.reviewSkill(empId, skillId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue((boolean) ((Map<?, ?>) responseEntity.getBody()).get("success"));
    }

   
}
