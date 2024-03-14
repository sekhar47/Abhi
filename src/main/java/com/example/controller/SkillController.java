package com.example.controller;

import java.util.List;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.dto.UpdateSkillForm;
import com.example.entity.Skills;
import com.example.service.SkillService;

@Controller
public class SkillController {

	@Autowired
    private HttpSession session;
	
    @Autowired
    private SkillService service;

    @GetMapping("/viewSkillsPage")
    public String viewSkillsPage(Model model) {
        List<Skills> skills = service.getSkills();
        model.addAttribute("skills", skills);
        return "viewSkills";
    }

    @GetMapping("/createDomainPage")
    public String createDomainPage(Model model) {
        List<String> domains = service.getDistinctDomains();
        model.addAttribute("domains", domains);
        return "createDomain"; // Assuming "createDomain" is your HTML template name
    }

    @GetMapping("/createSubdomainPage")
    public String createSubdomainPage(Model model) {
        List<String> domains = service.getDistinctDomains();
        model.addAttribute("domains", domains);
        return "createSubdomain";
    }

    @GetMapping("/createSkillPage")
    public String createSkillPage(Model model) {
        List<String> domains = service.getDistinctDomains();
        model.addAttribute("domains", domains);

        // Add an empty Skills object to the model
        model.addAttribute("skill", new Skills());

        return "createSkill";
    }

    @PostMapping("/saveSkill")
    public String saveSkill(@ModelAttribute Skills skill, RedirectAttributes redirectAttributes) {
        // Check if subdomain is null or empty
    	 String dataParam = (String) session.getAttribute("data");
         if (dataParam == null) {
             dataParam = ""; // Set default value if 'data' parameter is not set
         }
        
    	 if (skill.getSubdomain() == null) {
    	        // Redirect back to the skill creation page with an error message
    	        redirectAttributes.addFlashAttribute("errorMessage", "Please select a subdomain.");
    	        return "redirect:/viewSkillsPage?data=" + dataParam;
    	    }
        // Check if a skill with the same name, domain, and subdomain already exists
        Skills existingSkill = service.getSkillByNameAndDomainAndSubdomain(skill.getSkillname(), skill.getDomain(), skill.getSubdomain());

        if (existingSkill != null) {
            // If the skill already exists, update its properties
            existingSkill.setSkillname(skill.getSkillname());
            // Update other properties if needed

            // Save the updated skill
            service.saveSkill(existingSkill);
        } else {
            // If the skill doesn't exist, check if there is a matching row with the same domain and subdomain
            List<Skills> skillsInSameDomainAndSubdomain = service.findByDomainAndSubdomain(skill.getDomain(), skill.getSubdomain());

            if (!skillsInSameDomainAndSubdomain.isEmpty()) {
                // If there are existing skills in the same domain and subdomain, find the first one with an empty skillname
                Skills skillWithEmptyName = skillsInSameDomainAndSubdomain.stream()
                        .filter(s -> s.getSkillname() == null || s.getSkillname().trim().isEmpty())
                        .findFirst()
                        .orElse(null);

                if (skillWithEmptyName != null) {
                    // If found, update the existing skill with the new skillname
                    skillWithEmptyName.setSkillname(skill.getSkillname());
                    // Update other properties if needed

                    // Save the updated skill
                    service.saveSkill(skillWithEmptyName);
                } else {
                    // If no skill with an empty name is found, create a new skill
                    service.saveSkill(skill);
                }
            } else {
                // If there are no existing skills in the same domain and subdomain, create a new skill
                service.saveSkill(skill);
            }
        }

        return "redirect:/viewSkillsPage?data=" + dataParam;
    }



    
 
 
    @GetMapping("/getSubdomains/{domain}")
    @ResponseBody
    public List<String> getSubdomainsByDomain(@PathVariable String domain) {
        return service.getSubdomainsByDomain(domain);
    }

    @PostMapping("/saveDomain")
    public String saveDomain(@RequestParam("domain") String domain) {
        // Check if the domain already exists (case-insensitive)
        if (service.existsDomainIgnoreCase(domain)) {
            // If the domain already exists, you can handle this case, e.g., by redirecting with an error message
            return "redirect:/createDomainPage?error=Domain already exists";
        }

        // Save the new domain
        service.saveDomain(domain);

        String dataParam = (String) session.getAttribute("data");
        if (dataParam == null) {
            dataParam = ""; // Set default value if 'data' parameter is not set
        }
        // Save the subdomain
        return "redirect:/viewSkillsPage?data=" + dataParam;
    }
    
    
    @PostMapping("/saveSubdomain")
    public String saveSubdomain(@RequestParam("domain") String domain, @RequestParam("subdomain") String subdomain, Model model) {
        // Check if the subdomain already exists for the given domain (case-insensitive)
        if (service.existsSubdomainIgnoreCase(domain, subdomain)) {
            model.addAttribute("errorMessage", "Subdomain under the chosen domain already exists.");
            // Add logic to populate the domains list again and pass the model to the view
            List<String> domains = service.getDistinctDomains();
            model.addAttribute("domains", domains);
            return "createSubdomain";
        }
        String dataParam = (String) session.getAttribute("data");
        if (dataParam == null) {
            dataParam = ""; // Set default value if 'data' parameter is not set
        }
        // Save the subdomain
        service.saveSubdomain(domain, subdomain);
        return "redirect:/viewSkillsPage?data=" + dataParam;
    }

//    delete skill
    @PostMapping("/deleteSkills")
    public String deleteSkills(@RequestParam("skillsToDelete") List<Integer> skillIds, @RequestParam(name = "data", required = false, defaultValue = "") String dataParam) {
        // Iterate through the list of skillIds and delete each skill
        for (Integer skillId : skillIds) {
            service.deleteSkill(skillId);
        }
        return "redirect:/viewSkillsPage?data=" + dataParam;
    }
    
//    update skill
    @PostMapping("/updateSkill")
    public String updateSkill(@RequestParam("skillid") Integer skillId, @RequestParam("skillname") String skillName) {
        Skills existingSkill = service.getSkillById(skillId);
        if (existingSkill != null) {
            existingSkill.setSkillname(skillName);
            service.saveSkill(existingSkill);
        }
        String dataParam = (String) session.getAttribute("data");
        if (dataParam == null) {
            dataParam = ""; // Set default value if 'data' parameter is not set
        }
        return "redirect:/viewSkillsPage?data=" + dataParam;
    }

    
 // Update an existing domain
    @PostMapping("/updateDomain")
    public String updateDomain(@RequestParam("existingDomain") String existingDomain, @RequestParam("updatedDomain") String updatedDomain) {
        // Check if the updated domain is different from the existing one
        if (!existingDomain.equalsIgnoreCase(updatedDomain)) {
            // Check if the updated domain already exists (case-insensitive)
            if (service.existsDomainIgnoreCase(updatedDomain)) {
                // If the updated domain already exists, you can handle this case, e.g., by redirecting with an error message
                return "redirect:/createDomainPage?error=Domain already exists";
            }
            
            // Update the domain
            service.updateDomain(existingDomain, updatedDomain);
        }
        
        String dataParam = (String) session.getAttribute("data");
        if (dataParam == null) {
            dataParam = ""; // Set default value if 'data' parameter is not set
        }
        return "redirect:/viewSkillsPage?data=" + dataParam;
    }

    
    @PostMapping("/updateSubdomain")
    public String updateSubdomain(@RequestParam("domain") String domain, @RequestParam("existingSubdomain") String existingSubdomain, @RequestParam("updatedSubdomain") String updatedSubdomain) {
        // Check if the updated subdomain is different from the existing one
        if (!existingSubdomain.equalsIgnoreCase(updatedSubdomain)) {
            // Check if the updated subdomain already exists for the given domain (case-insensitive)
            if (service.existsSubdomainIgnoreCase(domain, updatedSubdomain)) {
                // If the updated subdomain already exists, you can handle this case, e.g., by redirecting with an error message
                return "redirect:/createSubdomainPage?error=Subdomain already exists";
            }
            
            // Update the subdomain
            service.updateSubdomain(domain, existingSubdomain, updatedSubdomain);
        }
        
        String dataParam = (String) session.getAttribute("data");
        if (dataParam == null) {
            dataParam = ""; // Set default value if 'data' parameter is not set
        }
        // Save the subdomain
        return "redirect:/viewSkillsPage?data=" + dataParam;
    }

    
//    update skillname
    @GetMapping("/updateSkillNamePage")
    public String updateSkillNamePage(Model model) {
        List<String> domains = service.getDistinctDomains();
        model.addAttribute("domains", domains);
        return "updateSkillName"; // Assuming "updateSkillName" is your HTML template name
    }

    

    @GetMapping("/getOldSkills/{domain}/{subdomain}")
    @ResponseBody
    public List<String> getOldSkills(@PathVariable String domain, @PathVariable String subdomain) {
        return service.getOldSkills(domain, subdomain);
    }

    @PostMapping("/updateSkillName")
    public String updateSkillName(@RequestParam("domain") String domain,
                                  @RequestParam("subdomain") String subdomain,
                                  @RequestParam("oldSkillName") String oldSkillName,
                                  @RequestParam("newSkillName") String newSkillName) {
    	service.updateSkillName(domain, subdomain, oldSkillName, newSkillName);
    	 String dataParam = (String) session.getAttribute("data");
         if (dataParam == null) {
             dataParam = ""; // Set default value if 'data' parameter is not set
         }
         return "redirect:/viewSkillsPage?data=" + dataParam;
    }
    

}
