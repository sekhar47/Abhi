package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.example.service.EmployeeService;
import com.example.dto.EmployeeDetailsDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/report")
public class ReportController {

    private final EmployeeService employeeService;

    public ReportController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "search";
    }

    @PostMapping("/generate")
    public String generateReport(
            @RequestParam(required = false) String empid,
            Model model,
            ModelMap modelMap
    ) {
        List<EmployeeDetailsDTO> employees = employeeService.findByEmpid(empid);

        if (employees.isEmpty()) {
            model.addAttribute("error", "Employee with EmpID " + empid + " not found.");
            return "search";
        } else {
            model.addAttribute("employees", employees);

            // Calculate data for charts
            Map<String, Integer> skillDistribution = calculateSkillDistribution(employees);
            modelMap.addAttribute("skillDistribution", skillDistribution);

            int reviewedCount = countReviewedSkills(employees);
            int totalSkillsCount = employees.size();
            int notReviewedCount = totalSkillsCount - reviewedCount;
            modelMap.addAttribute("reviewedCount", reviewedCount);
            modelMap.addAttribute("notReviewedCount", notReviewedCount);

            Map<String, Integer> trainingDaysMap = calculateTrainingDays(employees);
            modelMap.addAttribute("trainingDaysMap", trainingDaysMap);

            return "report";
        }
    }

 // Helper method to calculate skill distribution
    private Map<String, Integer> calculateSkillDistribution(List<EmployeeDetailsDTO> employees) {
        // Implement your logic to calculate skill distribution here
        // Example: Count the number of skills in each category (exert, novice, proficiency, awareness)
        Map<String, Integer> skillDistribution = new HashMap<>();
        // Populate skillDistribution map accordingly
        return skillDistribution;
    }

    // Helper method to count reviewed skills
    private int countReviewedSkills(List<EmployeeDetailsDTO> employees) {
        // Implement your logic to count reviewed skills
        int reviewedCount = 0;
        // Iterate through employees and count reviewed skills
        for (EmployeeDetailsDTO employee : employees) {
            if (employee.isReviewed()) {
                reviewedCount++;
            }
        }
        return reviewedCount;
    }

    // Helper method to calculate training days for each skill
    private Map<String, Integer> calculateTrainingDays(List<EmployeeDetailsDTO> employees) {
        // Implement your logic to calculate training days for each skill
        Map<String, Integer> trainingDaysMap = new HashMap<>();
        // Populate trainingDaysMap accordingly
        return trainingDaysMap;
    }
}
