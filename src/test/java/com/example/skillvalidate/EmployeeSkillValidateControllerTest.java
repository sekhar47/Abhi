package com.example.skillvalidate;

import com.example.entity.EmployeeSkill;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeSkillValidateControllerTest {

    @Mock
    private EmployeeSkillValidateService employeeSkillValidateService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private EmployeeSkillValidateController controller;

    @Test
    void testShowPendingAndRejectedEmployeeSkills() {
        List<EmployeeSkill> mockEmployeeSkills = Collections.singletonList(new EmployeeSkill());
        when(employeeSkillValidateService.getAllPendingAndRejectedEmployeeSkills()).thenReturn(mockEmployeeSkills);

        Model model = mock(Model.class);

        String viewName = controller.showPendingAndRejectedEmployeeSkills(model);

        assertEquals("employeeSkillsPage", viewName);
        verify(model).addAttribute("employeeSkills", mockEmployeeSkills);
    }

    @Test
    void testReviewSkill() {
        String empid = "testEmpId";
        Integer skillid = 1;
        boolean approved = true;

        String redirect = controller.reviewSkill(empid, skillid, approved);

        assertEquals("redirect:/employeeSkills", redirect);
        verify(employeeSkillValidateService).reviewEmployeeSkill(empid, skillid, approved);
    }

    @Test
    void testGetNotificationCount() {
        Integer mockNotificationCount = 42;
        when(session.getAttribute("notificationCount")).thenReturn(mockNotificationCount);

        ResponseEntity<Integer> responseEntity = controller.getNotificationCount();

        assertEquals(ResponseEntity.ok(mockNotificationCount), responseEntity);
    }
    
    @Test
    void testGetNotificationCount1() {
        Integer mockNotificationCount = 42;
        when(session.getAttribute("notificationCount")).thenReturn(mockNotificationCount);

        ResponseEntity<Integer> responseEntity = controller.getNotificationCount();

        assertEquals(ResponseEntity.ok(mockNotificationCount), responseEntity);
    }

    @Test
    void testGetNotificationCountWithNullCount() {
        when(session.getAttribute("notificationCount")).thenReturn(null);

        ResponseEntity<Integer> responseEntity = controller.getNotificationCount();

        assertEquals(ResponseEntity.ok(0), responseEntity);
    }

    @Test
    void testGetNotificationCountException() {
        when(session.getAttribute("notificationCount")).thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, () -> controller.getNotificationCount());
    }
}
