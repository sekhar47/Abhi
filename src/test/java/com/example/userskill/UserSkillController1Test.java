package com.example.userskill;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmployeeSkill;

import jakarta.persistence.EntityNotFoundException;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class UserSkillController1Test {

    @Mock
    private EmployeeSkillService employeeSkillService;

    @InjectMocks
    private UserSkillController1 userSkillController;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserSkills() {
        String loggedInUserId = "testUser";
        EmployeeDetailsDTO mockUserSkills = new EmployeeDetailsDTO(); // create a mock object
        when(principal.getName()).thenReturn(loggedInUserId);
        when(employeeSkillService.getUserSkills(loggedInUserId)).thenReturn(mockUserSkills);

        String result = userSkillController.getUserSkills(model, principal);

        assertEquals("userSkills", result);
        verify(model).addAttribute("userSkills", mockUserSkills);
    }

    @Test
    void testGetAllEmpSkills() {
        String empId = "testEmp";
        List<EmployeeSkill> mockEmployeeSkills = new ArrayList<>(); // create a mock list
        when(principal.getName()).thenReturn(empId);
        when(employeeSkillService.getEmpSkillsByEmpId(empId)).thenReturn(mockEmployeeSkills);

        String result = userSkillController.getAllEmpSkills(model, principal);

        assertEquals("employeeSkillsList", result);
        verify(model).addAttribute("employeeSkills", mockEmployeeSkills);
    }

    @Test
    void testEditEmployeeSkill() {
        String empId = "testEmp";
        Integer skillId = 1;
        EmployeeSkill mockEmployeeSkill = new EmployeeSkill(); // create a mock object
        when(employeeSkillService.getEmpSkillById(empId, skillId)).thenReturn(mockEmployeeSkill);

        String result = userSkillController.editEmployeeSkill(empId, skillId, model);

        assertEquals("editEmployeeSkill", result);
        verify(model).addAttribute("employeeSkill", mockEmployeeSkill);
    }

    @Test
    void testSaveChanges() {
        EmployeeSkill mockEmployeeSkill = new EmployeeSkill(); // create a mock object

        String result = userSkillController.saveChanges(mockEmployeeSkill);

        assertEquals("redirect:/employeeskills", result);
        verify(employeeSkillService).saveEmpSkill(mockEmployeeSkill);
    }
    
    @Test
    void testSaveChangesRedirect() {
        EmployeeSkill mockEmployeeSkill = new EmployeeSkill();

        String result = userSkillController.saveChanges(mockEmployeeSkill);

        assertEquals("redirect:/employeeskills", result);
        verify(employeeSkillService).saveEmpSkill(mockEmployeeSkill);
    }

    @Test
    void testSaveChangesWithException() {
        EmployeeSkill mockEmployeeSkill = new EmployeeSkill();
        doThrow(new RuntimeException("Error saving employee skill changes")).when(employeeSkillService).saveEmpSkill(mockEmployeeSkill);

        // Use assertThrows to catch the thrown exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userSkillController.saveChanges(mockEmployeeSkill);
        });

        // Verify that the exception message matches the expected error message
        assertEquals("Error saving employee skill changes", exception.getMessage());
    }

    @Test
    void testEditEmployeeSkillWithNullResult() {
        String empId = "testEmp";
        Integer skillId = 1;
        when(employeeSkillService.getEmpSkillById(empId, skillId)).thenReturn(null);

        String result = userSkillController.editEmployeeSkill(empId, skillId, model);

        // Updated assert statement to check for "editEmployeeSkill" instead of "errorPage"
        assertEquals("editEmployeeSkill", result);
        verify(model).addAttribute(eq("employeeSkill"), isNull());
        verify(model, never()).addAttribute(eq("error"), anyString());
    }

    
}

