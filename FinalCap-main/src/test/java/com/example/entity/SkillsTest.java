package com.example.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SkillsTest {

    private Skills skills;

    @BeforeEach
    public void setUp() {
        skills = new Skills();
    }

    @Test
    public void testConstructor() {
        // Given
        Integer skillId = 1;
        String skillName = "Java";
        String subdomain = "Backend";
        String domain = "Programming";

        // When
        Skills newSkills = new Skills(skillId, skillName, subdomain, domain, new HashSet<>());

        // Then
        assertNotNull(newSkills);
        assertEquals(skillId, newSkills.getSkillid());
        assertEquals(skillName, newSkills.getSkillname());
        assertEquals(subdomain, newSkills.getSubdomain());
        assertEquals(domain, newSkills.getDomain());
    }

    @Test
    public void testGetterAndSetter() {
        // Given
        Integer skillId = 1;
        String skillName = "Java";
        String subdomain = "Backend";
        String domain = "Programming";

        // When
        skills.setSkillid(skillId);
        skills.setSkillname(skillName);
        skills.setSubdomain(subdomain);
        skills.setDomain(domain);

        // Then
        assertEquals(skillId, skills.getSkillid());
        assertEquals(skillName, skills.getSkillname());
        assertEquals(subdomain, skills.getSubdomain());
        assertEquals(domain, skills.getDomain());
    }

    @Test
    public void testEmployeeSkills() {
        // Given
        Set<EmployeeSkill> employeeSkills = new HashSet<>();
        EmployeeSkill skill1 = new EmployeeSkill();
        EmployeeSkill skill2 = new EmployeeSkill();
        employeeSkills.add(skill1);
        employeeSkills.add(skill2);

        // When
        skills.setEmployeeSkills(employeeSkills);

        // Then
        assertEquals(employeeSkills, skills.getEmployeeSkills());
    }
}
