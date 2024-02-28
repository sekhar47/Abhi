package com.example.service;
import com.example.entity.Skills;
import com.example.repository.Skillrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkillServiceTest {

    @Mock
    private Skillrepo repository;

    @InjectMocks
    private SkillService skillService;

    private Skills skill;

    @BeforeEach
    void setUp() {
        skill = new Skills();
        skill.setSkillid(1);
        skill.setSkillname("Java");
        skill.setSubdomain("Backend");
        skill.setDomain("Programming");
    }

    @Test
    void testSaveSkill() {
        // Given
        when(repository.save(skill)).thenReturn(skill);

        // When
        Skills savedSkill = skillService.saveSkill(skill);

        // Then
        assertNotNull(savedSkill);
        assertEquals(skill.getSkillname(), savedSkill.getSkillname());
    }

    @Test
    void testGetSkillById() {
        // Given
        when(repository.findById(skill.getSkillid())).thenReturn(Optional.of(skill));

        // When
        Skills retrievedSkill = skillService.getSkillById(skill.getSkillid());

        // Then
        assertNotNull(retrievedSkill);
        assertEquals(skill.getSkillid(), retrievedSkill.getSkillid());
    }

    // Add similar test methods for other service methods
    
    // Example:
    @Test
    void testGetSkillByName() {
        // Given
        when(repository.findBySkillname(skill.getSkillname())).thenReturn(Optional.of(skill));

        // When
        Skills retrievedSkill = skillService.getSkillByName(skill.getSkillname());

        // Then
        assertNotNull(retrievedSkill);
        assertEquals(skill.getSkillname(), retrievedSkill.getSkillname());
    }
}
