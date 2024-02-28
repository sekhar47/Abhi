package com.example.userskill;


import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmpID;
import com.example.entity.EmployeeSkill;
import com.example.repository.Empskillrepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeSkillService {

    private final Empskillrepo empskillrepo;

    @Autowired
    public EmployeeSkillService(Empskillrepo empskillrepo) {
        this.empskillrepo = empskillrepo;
    }

    public EmployeeDetailsDTO getUserSkills(String empid) {
        return empskillrepo.findUserSkills(empid);
    }


    public EmployeeSkill getEmpSkillById(String empId, Integer skillId) {
        return empskillrepo.findById(new EmpID(empId, skillId)).orElse(null);
    }
 
    public void saveEmpSkill(EmployeeSkill Eskill) {
    	empskillrepo.save(Eskill);
	}
   
    public List<EmployeeSkill> getEmpSkillsByEmpId(String empId) {
        return empskillrepo.findById_Empid(empId);
    }
}
