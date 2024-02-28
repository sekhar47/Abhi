package com.example.skillvalidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.EmployeeSkill;

@Controller
public class EmployeeSkillValidateController {

    @Autowired
    private EmployeeSkillValidateService employeeSkillValidateService;

    @GetMapping("/employeeSkills")
    public String showEmployeeSkills(Model model) {
        List<EmployeeSkill> employeeSkills = employeeSkillValidateService.getAllEmployeeSkills();
        model.addAttribute("employeeSkills", employeeSkills);
        return "employeeSkillsPage"; // Assuming the name of your JSP file is employeeSkillsPage.jsp
    }

    @PostMapping("/reviewSkill")
    public String reviewSkill(@RequestParam String empid, @RequestParam Integer skillid) {
        employeeSkillValidateService.reviewEmployeeSkill(empid, skillid);
        return "redirect:/employeeSkills"; // Redirect back to the page displaying employee skills
    }
}
