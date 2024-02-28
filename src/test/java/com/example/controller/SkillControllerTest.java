package com.example.controller;

import com.example.dto.UpdateSkillForm;
import com.example.entity.Skills;
import com.example.service.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SkillControllerTest {

    @Mock
    private SkillService skillServiceMock;

    @Mock
    private Model modelMock;

    @Mock
    private RedirectAttributes redirectAttributesMock;

    @InjectMocks
    private SkillController skillController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void viewSkillsPage_ReturnsViewSkillsPage() {
        // Arrange
        List<Skills> skills = new ArrayList<>();
        when(skillServiceMock.getSkills()).thenReturn(skills);

        // Act
        String viewName = skillController.viewSkillsPage(modelMock);

        // Assert
        assertEquals("viewSkills", viewName);
        verify(modelMock).addAttribute("skills", skills);
    }

    @Test
    void createDomainPage_ReturnsCreateDomainPage() {
        // Arrange
        List<String> domains = new ArrayList<>();
        when(skillServiceMock.getDistinctDomains()).thenReturn(domains);

        // Act
        String viewName = skillController.createDomainPage(modelMock);

        // Assert
        assertEquals("createDomain", viewName);
        verify(modelMock).addAttribute("domains", domains);
    }

    @Test
    void createSubdomainPage_ReturnsCreateSubdomainPage() {
        // Arrange
        List<String> domains = new ArrayList<>();
        when(skillServiceMock.getDistinctDomains()).thenReturn(domains);

        // Act
        String viewName = skillController.createSubdomainPage(modelMock);

        // Assert
        assertEquals("createSubdomain", viewName);
        verify(modelMock).addAttribute("domains", domains);
    }

 
    @Test
    void saveSkill_RedirectsToViewSkillsPage_WhenSkillDoesNotExist() {
        // Arrange
        Skills skill = new Skills();
        skill.setSkillname("Java");
        skill.setDomain("Programming");
        skill.setSubdomain("Backend");
        when(skillServiceMock.getSkillByNameAndDomainAndSubdomain("Java", "Programming", "Backend")).thenReturn(null);

        // Act
        String viewName = skillController.saveSkill(skill, redirectAttributesMock);

        // Assert
        assertEquals("redirect:/viewSkillsPage", viewName);
        verify(skillServiceMock).saveSkill(skill);
    }

    // Add more test cases for other methods
}
