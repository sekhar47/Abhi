package com.example.userskill;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmpID;
import com.example.entity.EmployeeSkill;
import com.example.entity.Skills;
import com.example.entity.User;
import com.example.repository.Empskillrepo;
import com.example.repository.Skillrepo;
import com.example.repository.Userrepo;

import jakarta.transaction.Transactional;
@Service
public class UserSkillService {
    
    @Autowired
    private Empskillrepo employeeSkillRepository;
    
    @Autowired
    private Userrepo userRepository;
    
    @Autowired
    private Skillrepo skillRepository;

    // Method to retrieve all domains
    public List<String> getAllDomains() {
        List<String> domains = new ArrayList<>();
        List<Skills> allSkills = skillRepository.findAll();
        for (Skills skill : allSkills) {
            String domain = skill.getDomain();
            if (!domains.contains(domain)) {
                domains.add(domain);
            }
        }
        return domains;
    }
    
 // Method to retrieve all subdomains based on selected domain
    public List<String> getAllSubdomains(String selectedDomain) {
        List<String> subdomains = new ArrayList<>();
        List<Skills> skillsInDomain = skillRepository.findByDomain(selectedDomain);
        for (Skills skill : skillsInDomain) {
            String subdomain = skill.getSubdomain();
            if (!subdomains.contains(subdomain)) {
                subdomains.add(subdomain);
            }
        }
        return subdomains;
    }

    // Method to retrieve all skills based on selected subdomain
    public List<String> getAllSkills(String selectedSubdomain) {
        List<String> skills = new ArrayList<>();
        List<Skills> skillsInSubdomain = skillRepository.findBySubdomain(selectedSubdomain);
        for (Skills skill : skillsInSubdomain) {
            skills.add(skill.getSkillname()); // Assuming skillName is the name of the skill
        }
        return skills;
    }

    // Method to add skill
    public void addSkill(EmployeeDetailsDTO skillDTO, String empId) {
        User user = userRepository.findByEmpid(empId);
        Skills skill = skillRepository.findBySkillnameAndDomainAndSubdomain(
                skillDTO.getSkillname(), skillDTO.getDomain(), skillDTO.getSubdomain())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        // Check if the user already has the same skill with the same proficiency
        if (employeeSkillRepository.existsById(new EmpID(empId, skill.getSkillid()))) {
            throw new RuntimeException("User already has the same skill");
        }

        EmployeeSkill employeeSkill = new EmployeeSkill();
        employeeSkill.setId(new EmpID(empId, skill.getSkillid()));
        employeeSkill.setUser(user);
        employeeSkill.setSkills(skill);
        employeeSkill.setProficiency(skillDTO.getProficiency());
        employeeSkill.setCertificationname(skillDTO.getCertificationname());
        employeeSkill.setReviewed(false);
        employeeSkill.setTrainingdays(skillDTO.getTrainingdays());

        employeeSkillRepository.save(employeeSkill);
    }


    public EmployeeDetailsDTO getSkillDetails(String skillName) {
        // Fetch skill details by skillName from repository
        EmployeeSkill employeeSkill = employeeSkillRepository.findBySkillsSkillname(skillName)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        // Create and populate the DTO
        EmployeeDetailsDTO skillDTO = new EmployeeDetailsDTO();
        skillDTO.setSkillname(employeeSkill.getSkills().getSkillname());
        skillDTO.setDomain(employeeSkill.getSkills().getDomain());
        skillDTO.setSubdomain(employeeSkill.getSkills().getSubdomain());
        skillDTO.setCertificationname(employeeSkill.getCertificationname()); // Update: Rename to CertificationName
        skillDTO.setProficiency(employeeSkill.getProficiency());
        skillDTO.setTrainingdays(employeeSkill.getTrainingdays()); // Update: Rename to TrainingDays

        return skillDTO;
    }




 // Inside UserSkillService

    public List<EmployeeDetailsDTO> getUserSkills(String empid) {
        // Fetch user skills based on empId
        List<EmployeeDetailsDTO> userSkills = new ArrayList<>();

        // Fetch the user by empId
        User user = userRepository.findByEmpid(empid);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Fetch all skills associated with the user, including domain, subdomain, and skillname
        List<EmployeeSkill> employeeSkills = employeeSkillRepository.findById_Empid(empid);
        for (EmployeeSkill employeeSkill : employeeSkills) {
            EmployeeDetailsDTO skillDTO = new EmployeeDetailsDTO();
            skillDTO.setSkillid(employeeSkill.getId().getSkillid());
            skillDTO.setSkillname(employeeSkill.getSkills().getSkillname());
            skillDTO.setSubdomain(employeeSkill.getSkills().getSubdomain());
            skillDTO.setDomain(employeeSkill.getSkills().getDomain());
            skillDTO.setProficiency(employeeSkill.getProficiency());
            skillDTO.setCertificationname(employeeSkill.getCertificationname());
            skillDTO.setTrainingdays(employeeSkill.getTrainingdays());
            skillDTO.setReviewed(employeeSkill.isReviewed());
            userSkills.add(skillDTO);
        }

        return userSkills;
    }

	 

    @Transactional
    public void deleteUserSkills(String empId, List<Integer> skillIds) {
        for (Integer skillId : skillIds) {
            employeeSkillRepository.deleteById_EmpidAndId_Skillid(empId, skillId);
        }
    }


 
    
}
