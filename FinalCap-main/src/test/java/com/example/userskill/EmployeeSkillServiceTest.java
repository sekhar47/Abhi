package com.example.userskill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmpID;
import com.example.entity.EmployeeSkill;
import com.example.repository.Empskillrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

public class EmployeeSkillServiceTest {

    private Empskillrepo empskillrepo;
    private EmployeeSkillService employeeSkillService;

    @BeforeEach
    void setUp() {
        empskillrepo = mock(Empskillrepo.class);
        employeeSkillService = new EmployeeSkillService(empskillrepo);
    }

    @Test
    void testGetUserSkills() {
        // Arrange
        String empId = "sampleEmpId";
        when(empskillrepo.findUserSkills(empId)).thenReturn(new EmployeeDetailsDTO(/* Mocked DTO */));

        // Act
        EmployeeDetailsDTO result = employeeSkillService.getUserSkills(empId);

        // Assert
        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void testGetEmpSkillById() {
        // Arrange
        String empId = "sampleEmpId";
        Integer skillId = 1;
        
        // Mocking the findById method to return an Optional with a mocked EmployeeSkill
        EmployeeSkill mockedSkill = new EmployeeSkill();  // Create a mocked skill
        when(empskillrepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(mockedSkill));

        // Act
        EmployeeSkill result = employeeSkillService.getEmpSkillById(empId, skillId);

        // Print information for diagnosis
        System.out.println("mockedSkill: " + mockedSkill);
        System.out.println("result: " + result);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(mockedSkill, result, "Returned skill should be the mocked skill");

        // Verify that findById was called with the expected arguments
        verify(empskillrepo, times(1)).findById(ArgumentMatchers.any());
    }

    
    @Test
    void testSaveEmpSkill() {
        // Arrange
        EmployeeSkill employeeSkill = new EmployeeSkill();

        // Act
        employeeSkillService.saveEmpSkill(employeeSkill);

        // Assert
        verify(empskillrepo, times(1)).save(employeeSkill);
    }

    @Test
    void testGetEmpSkillsByEmpId() {
        // Arrange
        String empId = "sampleEmpId";
        List<EmployeeSkill> mockedSkills = Collections.singletonList(new EmployeeSkill());
        when(empskillrepo.findById_Empid(empId)).thenReturn(mockedSkills);

        // Act
        List<EmployeeSkill> result = employeeSkillService.getEmpSkillsByEmpId(empId);

        // Print information for diagnosis
        System.out.println("mockedSkills: " + mockedSkills);
        System.out.println("result: " + result);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(mockedSkills, result, "Returned skills should be the mocked skills");

        // Verify that findById_Empid was called with the expected arguments
        verify(empskillrepo, times(1)).findById_Empid(empId);
    }

}
