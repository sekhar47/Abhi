package com.example.userskill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.EmployeeDetailsDTO;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private UserSkillService userSkillService;

    @GetMapping("/subdomains")
    public ResponseEntity<List<String>> getSubdomains(@RequestParam("domain") String domain) {
        List<String> subdomains = userSkillService.getAllSubdomains(domain);
        return ResponseEntity.ok(subdomains);
    }

    @GetMapping("/skills")
    public ResponseEntity<List<String>> getSkills(@RequestParam("subdomain") String subdomain) {
        List<String> skills = userSkillService.getAllSkills(subdomain);
        return ResponseEntity.ok(skills);
    }
    


}
