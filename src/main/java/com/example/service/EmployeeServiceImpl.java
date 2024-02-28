package com.example.service;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmployeeSkill;
import com.example.entity.EmpID;
import com.example.entity.User;
import com.example.entity.Skills;
import com.example.repository.Empskillrepo;
import com.example.repository.Skillrepo;
import com.example.repository.Userrepo;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.client.ResourceAccessException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Empskillrepo employeeSkillRepository;
    private final Userrepo userRepository;
    private final Skillrepo skillsRepository;

    public EmployeeServiceImpl(Empskillrepo employeeSkillRepository,
    							Userrepo userRepository,
                                Skillrepo skillsRepository) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.userRepository = userRepository;
        this.skillsRepository = skillsRepository;
    }

    @Override
    public List<EmployeeDetailsDTO> getAllEmployeeDetails() {
        List<EmployeeSkill> employeeSkills = employeeSkillRepository.findAll();

        // Map EmployeeSkill entities to EmployeeDetailsDTO
        return employeeSkills.stream()
                .map(this::mapToEmployeeDetailsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDetailsDTO> findByEmpid(String empid) {
        List<EmployeeSkill> employeeSkills = employeeSkillRepository.findByUserEmpid(empid);
        return employeeSkills.stream()
                .map(this::mapToEmployeeDetailsDTO)
                .collect(Collectors.toList());
    }


    
//    filter
    @Override
    public List<EmployeeDetailsDTO> searchEmployees(String empid, String skillname, String domain, String subdomain, String proficiency) {
        List<EmployeeSkill> employeeSkills = employeeSkillRepository.findByUserEmpidIgnoreCaseContainingAndSkillsSkillnameIgnoreCaseContainingAndSkillsDomainIgnoreCaseContainingAndSkillsSubdomainIgnoreCaseContainingAndProficiencyIgnoreCaseContaining(
                empid,
                skillname,
                domain,
                subdomain,
                proficiency
        );

        return employeeSkills.stream()
                .map(this::mapToEmployeeDetailsDTO)
                .collect(Collectors.toList());
    }
    
    
    
    private EmployeeDetailsDTO mapToEmployeeDetailsDTO(EmployeeSkill employeeSkill) {
        EmpID empId = employeeSkill.getId();
        Optional<User> optionalUser = userRepository.findById(empId.getEmpid());
        Optional<Skills> optionalSkills = skillsRepository.findById(empId.getSkillid());

        return optionalUser.map(user -> {
            EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
            employeeDetailsDTO.setEmpid(user.getEmpid());
            employeeDetailsDTO.setName(user.getName());
            employeeDetailsDTO.setEmpemail(user.getEmpemail());
            employeeDetailsDTO.setEmpmobile(user.getEmpmobile());
            employeeDetailsDTO.setSkillname(optionalSkills.map(Skills::getSkillname).orElse(null));
            employeeDetailsDTO.setDomain(optionalSkills.map(Skills::getDomain).orElse(null));
            employeeDetailsDTO.setSubdomain(optionalSkills.map(Skills::getSubdomain).orElse(null));
            employeeDetailsDTO.setProficiency(employeeSkill.getProficiency());
            employeeDetailsDTO.setAvailability(user.getAvailability());
            employeeDetailsDTO.setReviewed(employeeSkill.isReviewed());
            employeeDetailsDTO.setTrainingdays(employeeSkill.getTrainingdays());
            return employeeDetailsDTO;
        }).orElse(null);
    }
    
    @Override
    public void reviewSkill(String empid, Integer skillid) {
        Optional<EmployeeSkill> optionalEmployeeSkill = employeeSkillRepository.findById_EmpidAndId_Skillid(empid, skillid);
        if (optionalEmployeeSkill.isPresent()) {
            EmployeeSkill employeeSkill = optionalEmployeeSkill.get();
            employeeSkill.setReviewed(true);
            employeeSkillRepository.save(employeeSkill);
        } else {
            throw new NoSuchElementException("Employee skill not found.");
        }
    }

	@Override
	public String getname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getempid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getempemail() {
		// TODO Auto-generated method stub
		return null;
	}

   
}
