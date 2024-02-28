package com.example.service;
import com.example.dto.EmployeeDetailsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private List<EmployeeDetailsDTO> employeeDetailsDTOList;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

 
    @Test
    void testGetName() {
        // Given

        // When
        String result = employeeService.getname();

        // Then
        // Add assertions or verify interactions with mock dependencies
    }

    @Test
    void testGetEmpid() {
        // Given

        // When
        String result = employeeService.getempid();

        // Then
        // Add assertions or verify interactions with mock dependencies
    }

    @Test
    void testGetEmpemail() {
        // Given

        // When
        String result = employeeService.getempemail();

        // Then
        // Add assertions or verify interactions with mock dependencies
    }
}
