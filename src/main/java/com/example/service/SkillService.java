package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Skills;
import com.example.repository.Skillrepo;

import jakarta.transaction.Transactional;

@Service
public class SkillService {
	@Autowired
	private Skillrepo repository;
	
	public Skills saveSkill(Skills skill) {
		return repository.save(skill);
	}
	public List<Skills> saveSkills(List<Skills> skill) {
		return repository.saveAll(skill);
	}
	public List<Skills> getSkills(){
		return repository.findAll();
	}
	
	public Skills getSkillById(int skillid){
		return repository.findById(skillid).orElse(null);
	}
	public Skills getSkillByName(String skillname){
		return repository.findBySkillname(skillname).orElse(null);
	}


	
	// Save a single subdomain
	 public void saveSubdomain(String domain, String subdomain) {
	        // Find a row with the specified domain
	        List<Skills> skillsWithDomain = repository.findByDomain(domain);

	        if (!skillsWithDomain.isEmpty()) {
	            // If found, check if any row has an empty subdomain
	            Skills existingSkillWithEmptySubdomain = skillsWithDomain.stream()
	                .filter(skill -> skill.getSubdomain() == null || skill.getSubdomain().trim().isEmpty())
	                .findFirst()
	                .orElse(null);

	            if (existingSkillWithEmptySubdomain != null) {
	                // If an existing row with an empty subdomain is found, update it
	                existingSkillWithEmptySubdomain.setSubdomain(subdomain);
	                repository.save(existingSkillWithEmptySubdomain);
	            } else {
	                // If no existing row with an empty subdomain is found, create a new row
	                Skills newSkill = new Skills();
	                newSkill.setDomain(domain);
	                newSkill.setSubdomain(subdomain);
	                repository.save(newSkill);
	            }
	        } else {
	            // If no existing row with the specified domain is found, create a new row
	            Skills newSkill = new Skills();
	            newSkill.setDomain(domain);
	            newSkill.setSubdomain(subdomain);
	            repository.save(newSkill);
	        }
	    }


   
	
    public List<Skills> findByDomain(String domain) {
        return repository.findByDomain(domain);
    }

    public List<Skills> findByDomainAndSubdomain(String domain, String subdomain) {
        return repository.findByDomainAndSubdomain(domain, subdomain);
    }

    public List<String> getDistinctDomains() {
        return repository.findDistinctDomains();
    }

    public List<String> getSubdomainsByDomain(String domain) {
        return repository.findSubdomainsByDomain(domain);
    }
    
    public Skills getSkillByNameAndDomainAndSubdomain(String skillname, String domain, String subdomain) {
        return repository.findBySkillnameAndDomainAndSubdomain(skillname, domain, subdomain).orElse(null);
    }
    
    public List<Skills> getSkillsByDomainSubdomainAndSkillname(String domain, String subdomain, String skillname) {
        return repository.findByDomainAndSubdomainAndSkillname(domain, subdomain, skillname);
    }

    
 // Check if a subdomain already exists for the given domain (case-insensitive)
    public boolean existsSubdomainIgnoreCase(String domain, String subdomain) {
        String lowercaseSubdomain = subdomain.toLowerCase(); // Convert to lowercase for case-insensitive check
        List<Skills> skills = repository.findByDomainAndSubdomainIgnoreCase(domain, lowercaseSubdomain);
        return !skills.isEmpty();
    }
    
    
    public void saveDomain(String domain) {
        Skills skillDomain = new Skills();
        skillDomain.setDomain(domain);
        repository.save(skillDomain);
    }

    public boolean existsDomainIgnoreCase(String domain) {
        return repository.existsByDomainIgnoreCase(domain);
    }
    
  
    public List<String> getDomains() {
        // Implement logic to retrieve distinct domains from the database
        return repository.findDistinctDomains();
    }

    

    public List<String> getSkillNamesBySubdomain(String subdomain) {
        // Implement logic to retrieve skill names based on the selected subdomain from the database
        return repository.findSkillNamesBySubdomain(subdomain);
    }

 
    
    public String deleteSkill(int skillid) {
        repository.deleteById(skillid);
        return "Skill removed!! " + skillid;
    }
    
    public void updateSkill(Skills skill) {
        repository.save(skill);
    }

    
    
//   updatedomain
    @Transactional
    public void updateDomain(String existingDomain, String updatedDomain) {
     
    	repository.updateDomain(existingDomain, updatedDomain);
    }
    
    
//    update subdomain
    @Transactional
    public void updateSubdomain(String domain, String existingSubdomain, String updatedSubdomain) {
        // Perform any necessary validation or business logic
        
        // Update the subdomain
        repository.updateSubdomain(domain, existingSubdomain, updatedSubdomain);
    }

    
//    update killname

   
    public List<String> getOldSkills(String domain, String subdomain) {
        return repository.findOldSkills(domain, subdomain);
    }
    
    @Transactional
    public void updateSkillName(String domain, String subdomain, String oldSkillName, String newSkillName) {
    	repository.updateSkillName(domain, subdomain, oldSkillName, newSkillName);
    }

    
    
}
