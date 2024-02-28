package com.example.userskill;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmployeeSkill;
import com.example.entity.Skills;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    @GetMapping("/add-skill")
    public ModelAndView showAddSkillForm(Model model) {
        ModelAndView modelAndView = new ModelAndView("add-skill");
        model.addAttribute("skillDTO", new EmployeeDetailsDTO()); // Add skillDTO to the model
        return modelAndView;
    }

    @PostMapping("/add-skill")
    public ModelAndView addSkill(@ModelAttribute("skillDTO") EmployeeDetailsDTO skillDTO, Principal principal) {
        String empId = principal.getName(); // Assuming empId is stored as username in Principal

        ModelAndView modelAndView = new ModelAndView("add-skill");

        try {
            userSkillService.addSkill(skillDTO, empId);
            modelAndView.addObject("successMessage", "Skill added successfully");
        } catch (RuntimeException e) {
        	
        	modelAndView.addObject("errorMessage", "Skill already exists");
 // Add error message to the model
        }

        // Add the lists of domains, subdomains, and skills to the model
        modelAndView.addObject("domains", userSkillService.getAllDomains());
        modelAndView.addObject("subdomains", userSkillService.getAllSubdomains(skillDTO.getDomain()));
        modelAndView.addObject("skills", userSkillService.getAllSkills(skillDTO.getSubdomain()));

        modelAndView.addObject("skillDTO", new EmployeeDetailsDTO()); // Add skillDTO to clear the form fields after submission
        return modelAndView;
    }


    @ModelAttribute("domains")
    public List<String> getAllDomains() {
        return userSkillService.getAllDomains();
    }

    @ModelAttribute("subdomains")
    public List<String> getAllSubdomains(@RequestParam(value = "domain", required = false) String selectedDomain) {
        // Pass the selected domain to the service method to fetch subdomains
        if (selectedDomain != null) {
            return userSkillService.getAllSubdomains(selectedDomain);
        }
        return List.of(); // Return an empty list if no domain is selected
    }

    @ModelAttribute("skills")
    public List<String> getAllSkills(@RequestParam(value = "subdomain", required = false) String selectedSubdomain) {
        // Pass the selected subdomain to the service method to fetch skills
        if (selectedSubdomain != null) {
            return userSkillService.getAllSkills(selectedSubdomain);
        }
        return List.of(); // Return an empty list if no subdomain is selected
    }

    



    @GetMapping("/manageskills")
    public ModelAndView manageSkills(Model model, Principal principal) {
        String empId = principal.getName();
        List<EmployeeDetailsDTO> userSkills = userSkillService.getUserSkills(empId);
        ModelAndView modelAndView = new ModelAndView("manageskills");
        modelAndView.addObject("userSkills", userSkills);
        return modelAndView;
    }

    @GetMapping("/charts")
    public ModelAndView showCharts(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("chart");
        String empId = principal.getName();
        List<EmployeeDetailsDTO> userSkills = userSkillService.getUserSkills(empId);

        // Assuming you have methods to retrieve data for charts from your service
        List<String> skillNames = userSkills.stream().map(EmployeeDetailsDTO::getSkillname).collect(Collectors.toList());
        List<Integer> trainingDays = userSkills.stream().map(EmployeeDetailsDTO::getTrainingdays).collect(Collectors.toList());

        // Calculate total and reviewed skills for the pie chart
        int totalSkills = userSkills.size();
        long reviewedSkills = userSkills.stream().filter(EmployeeDetailsDTO::isReviewed).count();

        // Calculate proficiency level counts for the proficiency pie chart
        long expertSkills = userSkills.stream().filter(skill -> skill.getProficiency().equals("Expert")).count();
        long proficiencySkills = userSkills.stream().filter(skill -> skill.getProficiency().equals("Proficiency")).count();
        long awarenessSkills = userSkills.stream().filter(skill -> skill.getProficiency().equals("Awareness")).count();
        long noviceSkills = userSkills.stream().filter(skill -> skill.getProficiency().equals("Novice")).count();


        modelAndView.addObject("userSkills", userSkills);
        modelAndView.addObject("totalSkills", totalSkills);
        modelAndView.addObject("reviewedSkills", reviewedSkills);
        modelAndView.addObject("expertSkills", expertSkills);
        modelAndView.addObject("proficiencySkills", proficiencySkills);
        modelAndView.addObject("awarenessSkills", awarenessSkills);
        modelAndView.addObject("noviceSkills", noviceSkills);
        modelAndView.addObject("skillNames", skillNames);
        modelAndView.addObject("trainingDays", trainingDays);
        return modelAndView;
    }
    // Method to handle skill deletion

    @PostMapping("/delete-skills")
    public String deleteSkills(@RequestParam("skillIds") List<Integer> skillIds, Principal principal) {
        String empId = principal.getName();
        userSkillService.deleteUserSkills(empId, skillIds);
        return "redirect:/user/manageskills";
    }
    
}