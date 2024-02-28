package com.example.userskill;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmployeeSkill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class UserSkillController1 {

    private final EmployeeSkillService employeeSkillService;

    @Autowired
    public UserSkillController1(EmployeeSkillService employeeSkillService) {
        this.employeeSkillService = employeeSkillService;
    }

    @GetMapping("/employee/skills")
    public String getUserSkills(Model model, Principal principal) {
        String loggedInUserId = principal.getName();
        EmployeeDetailsDTO userSkills = employeeSkillService.getUserSkills(loggedInUserId);
        model.addAttribute("userSkills", userSkills);
        return "userSkills";
    }
    
    
    @GetMapping("/employeeskills")
    public String getAllEmpSkills(Model model, Principal principal) {
    	String empId = principal.getName(); // Get the empid from the authentication context
    	 List<EmployeeSkill> employeeSkills = employeeSkillService.getEmpSkillsByEmpId(empId);
        model.addAttribute("employeeSkills", employeeSkills);
        
        return "employeeSkillsList";
    }
 
    @PostMapping("/editEmployeeskill")
    public String editEmployeeSkill(@RequestParam String empid, @RequestParam Integer skillid, Model model) {
        EmployeeSkill employeeSkill = employeeSkillService.getEmpSkillById(empid, skillid);
        model.addAttribute("employeeSkill", employeeSkill);
        return "editEmployeeSkill";
    }
 
    @PostMapping("/saveChanges")
    public String saveChanges(EmployeeSkill employeeSkill) {
    	employeeSkillService.saveEmpSkill(employeeSkill);
        return "redirect:/employeeskills";
    }

}
