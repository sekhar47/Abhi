package com.example.controller;


import com.example.dto.EmployeeDetailsDTO;
import com.example.service.EmployeeService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/report")
public class ReportController {

    private final EmployeeService employeeService;

    @Autowired
    public ReportController(EmployeeService employeeService) {
        this.employeeService = employeeService;
        
    }

   
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public String showSearchPage() {
        return "search"; // This corresponds to the filename of your HTML for the search page
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public String generateReport(
            @RequestParam(required = false) String empid,
            Model model
    ) {
        List<EmployeeDetailsDTO> employees = employeeService.findByEmpid(empid);
        
        // Check if the list of employees is empty
        if (employees.isEmpty()) {
            // If no employees are found, add an error message to the model
            model.addAttribute("error", "Employee with EmpID " + empid + " not found.");
            // Return to the search page with the error message
            return "search";
        } else {
            // If employees are found, proceed to the report page
            model.addAttribute("employees", employees);
            return "report";
        }
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/generate")
//    public String showSearchPage1() {
//        return "search";
//    }

}
