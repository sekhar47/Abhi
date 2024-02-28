package com.example.service;
import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmployeeSkill;
import com.example.entity.EmpID;
import com.example.entity.User;
import com.example.entity.Skills;
import com.example.repository.Empskillrepo;
import com.example.repository.Skillrepo;
import com.example.repository.Userrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private Empskillrepo employeeSkillRepository;

    @Mock
    private Userrepo userRepository;

    @Mock
    private Skillrepo skillsRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployeeDetails() {
        // Given
        List<EmployeeSkill> mockEmployeeSkills = new ArrayList<>();
        when(employeeSkillRepository.findAll()).thenReturn(mockEmployeeSkills);

        // When
        List<EmployeeDetailsDTO> employeeDetailsDTOList = employeeService.getAllEmployeeDetails();

        // Then
        assertNotNull(employeeDetailsDTOList);
        assertEquals(0, employeeDetailsDTOList.size());
    }

    @Test
    void testFindByEmpid() {
        // Given
        String empid = "123";
        List<EmployeeSkill> mockEmployeeSkills = new ArrayList<>();
        when(employeeSkillRepository.findByUserEmpid(empid)).thenReturn(mockEmployeeSkills);

        // When
        List<EmployeeDetailsDTO> employeeDetailsDTOList = employeeService.findByEmpid(empid);

        // Then
        assertNotNull(employeeDetailsDTOList);
        assertEquals(0, employeeDetailsDTOList.size());
    }

    // Write similar test methods for other service methods...

    // Example test for reviewSkill method
    @Test
    void testReviewSkill() {
        // Given
        String empid = "123";
        Integer skillid = 456;
        EmployeeSkill mockEmployeeSkill = new EmployeeSkill();
        Optional<EmployeeSkill> optionalEmployeeSkill = Optional.of(mockEmployeeSkill);
        when(employeeSkillRepository.findById_EmpidAndId_Skillid(empid, skillid)).thenReturn(optionalEmployeeSkill);

        // When
        employeeService.reviewSkill(empid, skillid);

        // Then
        assertTrue(mockEmployeeSkill.isReviewed());
        verify(employeeSkillRepository, times(1)).save(mockEmployeeSkill);
    }

    @Test
    void testReviewSkill_NotFound() {
        // Given
        String empid = "123";
        Integer skillid = 456;
        when(employeeSkillRepository.findById_EmpidAndId_Skillid(empid, skillid)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(NoSuchElementException.class, () -> employeeService.reviewSkill(empid, skillid));
    }

}
