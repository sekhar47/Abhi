package com.example.skillvalidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.EmployeeSkill;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeSkillValidateController {

    @Autowired
    private EmployeeSkillValidateService employeeSkillValidateService;

    @Autowired
    private HttpSession session;

    @GetMapping("/employeeSkills")
    public String showPendingAndRejectedEmployeeSkills(Model model) {
        List<EmployeeSkill> employeeSkills = employeeSkillValidateService.getAllPendingAndRejectedEmployeeSkills();
        model.addAttribute("employeeSkills", employeeSkills);
        return "employeeSkillsPage"; // Assuming the name of your JSP file is employeeSkillsPage.jsp
    }

    @PostMapping("/reviewSkill")
    public String reviewSkill(@RequestParam String empid, @RequestParam Integer skillid, @RequestParam boolean approved) {
        employeeSkillValidateService.reviewEmployeeSkill(empid, skillid, approved);
        return "redirect:/employeeSkills"; // Redirect back to the page displaying employee skills
    }

    @GetMapping("/notificationCount")
    public ResponseEntity<Integer> getNotificationCount() {
        Integer count = (Integer) session.getAttribute("notificationCount");
        if (count == null) {
            count = 0;
        }
        return ResponseEntity.ok(count);
    }
}
