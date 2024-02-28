package com.example.entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeSkillTest {

    private EmployeeSkill employeeSkill;

    @BeforeEach
    public void setUp() {
        employeeSkill = new EmployeeSkill();
    }

    @Test
    public void testConstructor() {
        // Given
        EmpID empId = new EmpID();
        User user = new User();
        Skills skills = new Skills();
        String proficiency = "Expert";
        Boolean certified = true;
        String certificationName = "Java Certified";
        Boolean reviewed = true;
        int trainingDays = 10;

        // When
        EmployeeSkill newEmployeeSkill = new EmployeeSkill(empId, user, skills, proficiency, certified, certificationName, reviewed, trainingDays);

        // Then
        assertNotNull(newEmployeeSkill);
        assertEquals(empId, newEmployeeSkill.getId());
        assertEquals(user, newEmployeeSkill.getUser());
        assertEquals(skills, newEmployeeSkill.getSkills());
        assertEquals(proficiency, newEmployeeSkill.getProficiency());
        assertEquals(certified, newEmployeeSkill.getCertified());
        assertEquals(certificationName, newEmployeeSkill.getCertificationname());
        assertTrue(newEmployeeSkill.isReviewed());
        assertEquals(trainingDays, newEmployeeSkill.getTrainingdays());
    }

    @Test
    public void testGetterAndSetter() {
        // Given
        EmpID empId = new EmpID();
        User user = new User();
        Skills skills = new Skills();
        String proficiency = "Expert";
        Boolean certified = true;
        String certificationName = "Java Certified";
        Boolean reviewed = true;
        int trainingDays = 10;

        // When
        employeeSkill.setId(empId);
        employeeSkill.setUser(user);
        employeeSkill.setSkills(skills);
        employeeSkill.setProficiency(proficiency);
        employeeSkill.setCertified(certified);
        employeeSkill.setCertificationname(certificationName);
        employeeSkill.setReviewed(reviewed);
        employeeSkill.setTrainingdays(trainingDays);

        // Then
        assertEquals(empId, employeeSkill.getId());
        assertEquals(user, employeeSkill.getUser());
        assertEquals(skills, employeeSkill.getSkills());
        assertEquals(proficiency, employeeSkill.getProficiency());
        assertEquals(certified, employeeSkill.getCertified());
        assertEquals(certificationName, employeeSkill.getCertificationname());
        assertTrue(employeeSkill.isReviewed());
        assertEquals(trainingDays, employeeSkill.getTrainingdays());
    }
}
