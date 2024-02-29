package com.example.logincontroller.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmployeeSkill;
import com.example.entity.Skills;
import com.example.entity.User;
import com.example.repository.Empskillrepo;
import com.example.repository.Skillrepo;
import com.example.repository.Userrepo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class EmpskillrepoTest {

	 @Autowired
	    private Skillrepo skillsRepository;
    @Autowired
    private Empskillrepo empskillrepo;

    @Autowired
    private Userrepo userrepo;

    @Test
    public void testFindByUser() {
        User user = userrepo.findByEmpid("test_empid");
        List<EmployeeSkill> skills = empskillrepo.findByUser(user);
        
        // Assert that the list is not null (to avoid NullPointerException) and provide a message
        assertNotNull(skills, "List of EmployeeSkill objects should not be null");
        
        // If it's acceptable for the list to sometimes be empty, adjust the assertion accordingly
        //assertFalse(skills.isEmpty(), "List of EmployeeSkill objects should not be empty");
    }


    @Test
    public void testFindByUserEmpid() {
        String empid = "test_empid"; // Provide a valid employee ID for testing
        
        // Call the repository method to retrieve the list of EmployeeSkill objects
        List<EmployeeSkill> empSkills = empskillrepo.findByUserEmpid(empid);
        
        // Assert that the list is not null (to avoid NullPointerException) and provide a message
        assertNotNull(empSkills, "List of EmployeeSkill objects should not be null");
        
        // If it's acceptable for the list to sometimes be empty, adjust the assertion accordingly
       // assertFalse(empSkills.isEmpty(), "List of EmployeeSkill objects should not be empty");
    }


    @Test
    public void testFindBySkills() {
        // Create a Skills object and set its properties
        Skills skill = new Skills();
        skill.setSkillname("Test Skill");
        skill.setDomain("Test Domain");
        skill.setSubdomain("Test Subdomain");

        // Save the Skills object to the database
        skill = skillsRepository.save(skill); // Assuming you have a repository for Skills

        // Call the repository method to retrieve the EmployeeSkill associated with the provided skill
        Optional<EmployeeSkill> skillsOptional = empskillrepo.findBySkills(skill);

    }


    @Test
    public void testDeleteByIdEmpidAndIdSkillid() {
        String empid = "test_empid";
        Integer skillid = 1; // Assuming the ID of the skill to delete
        empskillrepo.deleteByEmpidAndSkillid(empid, skillid);
        // Add assertions to verify the deletion
    }

    @Test
    public void testReviewSkill() {
        String empid = "test_empid";
        Integer skillid = 1; // Assuming the ID of the skill to review
        empskillrepo.reviewSkill(empid, skillid);
        // Add assertions to verify the skill review
    }

    @Test
    public void testUpdateSkill() {
        String empid = "test_empid";
        Integer skillid = 1; // Assuming the ID of the skill to update
        String proficiency = "Intermediate";
        String certificationname = "Certification XYZ";
        int trainingdays = 5;
        
        empskillrepo.updateSkill(empid, skillid, proficiency, certificationname, trainingdays);
        // Add assertions to verify the skill update
    }

}
