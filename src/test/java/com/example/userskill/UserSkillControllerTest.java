package com.example.userskill;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;


import com.example.dto.EmployeeDetailsDTO;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserSkillControllerTest {

    @InjectMocks
    private UserSkillController userSkillController;

    @Mock
    private UserSkillService userSkillService;


    @Test
    public void testAddSkill_Success() {
        EmployeeDetailsDTO skillDTO = new EmployeeDetailsDTO();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testEmpId");

        ModelAndView result = userSkillController.addSkill(skillDTO, principal);

        assertEquals("add-skill", result.getViewName());
        assertEquals("Skill added successfully", result.getModel().get("successMessage"));
        verify(userSkillService, times(1)).addSkill(skillDTO, "testEmpId");
    }

    @Test
    public void testAddSkill_Failure() {
        EmployeeDetailsDTO skillDTO = new EmployeeDetailsDTO();
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testEmpId");
        doThrow(new RuntimeException("Skill already exists")).when(userSkillService).addSkill(skillDTO, "testEmpId");

        ModelAndView result = userSkillController.addSkill(skillDTO, principal);

        assertEquals("add-skill", result.getViewName());
        assertEquals("Skill already exists", result.getModel().get("errorMessage"));
        verify(userSkillService, times(1)).addSkill(skillDTO, "testEmpId");
    }
    
    @Test
    public void testManageSkills() {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testEmpId");

        List<EmployeeDetailsDTO> userSkills = new ArrayList<>();
        when(userSkillService.getUserSkills("testEmpId")).thenReturn(userSkills);

        ModelAndView result = userSkillController.manageSkills(model, principal);

        assertEquals("manageskills", result.getViewName());
        assertEquals(userSkills, result.getModel().get("userSkills"));
        verify(userSkillService, times(1)).getUserSkills("testEmpId");
    }

    @Test
    public void testShowCharts() {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testEmpId");

        List<EmployeeDetailsDTO> userSkills = new ArrayList<>();
        // Mock your userSkillService methods here to return necessary data

        ModelAndView result = userSkillController.showCharts(model, principal);

        assertEquals("chart", result.getViewName());
        // Add assertions for other model attributes and data returned from userSkillService
    }

    @Test
    public void testDeleteSkills() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testEmpId");

        // Set up mock data for skillIds
        List<Integer> skillIds = new ArrayList<>();
        skillIds.add(1);
        skillIds.add(2);

        // Call the controller method
        String viewName = userSkillController.deleteSkills(skillIds, principal);

        assertEquals("redirect:/user/manageskills", viewName);
        verify(userSkillService, times(1)).deleteUserSkills("testEmpId", skillIds);
    }
    
    @Test
    public void testModelAttributeDomains() {
        List<String> expectedDomains = new ArrayList<>();
        when(userSkillService.getAllDomains()).thenReturn(expectedDomains);

        List<String> result = userSkillController.getAllDomains();

        assertEquals(expectedDomains, result);
        verify(userSkillService, times(1)).getAllDomains();
    }

    @Test
    public void testModelAttributeSubdomains() {
        String selectedDomain = "selectedDomain";
        List<String> expectedSubdomains = new ArrayList<>();
        when(userSkillService.getAllSubdomains(selectedDomain)).thenReturn(expectedSubdomains);

        List<String> result = userSkillController.getAllSubdomains(selectedDomain);

        assertEquals(expectedSubdomains, result);
        verify(userSkillService, times(1)).getAllSubdomains(selectedDomain);
    }

    @Test
    public void testModelAttributeSkills() {
        String selectedSubdomain = "selectedSubdomain";
        List<String> expectedSkills = new ArrayList<>();
        when(userSkillService.getAllSkills(selectedSubdomain)).thenReturn(expectedSkills);

        List<String> result = userSkillController.getAllSkills(selectedSubdomain);

        assertEquals(expectedSkills, result);
        verify(userSkillService, times(1)).getAllSkills(selectedSubdomain);
    }
    
    
    // Add more test cases for other methods as needed
}
