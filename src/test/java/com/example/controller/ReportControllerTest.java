package com.example.controller;

import com.example.dto.EmployeeDetailsDTO;
import com.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReportControllerTest {

    @Mock
    private EmployeeService employeeServiceMock;

    @Mock
    private Model modelMock;

    private ReportController reportController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reportController = new ReportController(employeeServiceMock);
    }

    @Test
    void generateReport_ReturnsReportPage_WhenEmployeesFound() {
        // Arrange
        String empId = "123";
        List<EmployeeDetailsDTO> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new EmployeeDetailsDTO(1, "123", "John Doe", "john@example.com", "1234567890", "Java Developer", "IT", "Programming", "Expert", empId, true, true, 30, empId, empId, null, null));
        when(employeeServiceMock.findByEmpid(empId)).thenReturn(expectedEmployees);

        // Act
        String viewName = reportController.generateReport(empId, modelMock);

        // Assert
        assertEquals("report", viewName);
        verify(modelMock).addAttribute("employees", expectedEmployees);
    }

    @Test
    void generateReport_ReturnsSearchPageWithError_WhenNoEmployeesFound() {
        // Arrange
        String empId = "456";
        List<EmployeeDetailsDTO> expectedEmployees = new ArrayList<>();
        when(employeeServiceMock.findByEmpid(empId)).thenReturn(expectedEmployees);

        // Act
        String viewName = reportController.generateReport(empId, modelMock);

        // Assert
        assertEquals("search", viewName);
        verify(modelMock).addAttribute("error", "Employee with EmpID " + empId + " not found.");
    }
}
