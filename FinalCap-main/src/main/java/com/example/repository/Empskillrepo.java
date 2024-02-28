package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmpID;
import com.example.entity.EmployeeSkill;
import com.example.entity.Skills;
import com.example.entity.User;

import jakarta.transaction.Transactional;




@Repository
public interface Empskillrepo extends JpaRepository<EmployeeSkill, EmpID>{
	
	
	
	 List<EmployeeSkill> findByUser(User user);

	    Optional<EmployeeSkill> findByUserAndSkills(User user, Skills skills);

	    
//	    for search and filter module
    List<EmployeeSkill> findByUserEmpid(String empid);

	    
//	    filter
    List<EmployeeSkill> findByUserEmpidIgnoreCaseContainingAndSkillsSkillnameIgnoreCaseContainingAndSkillsDomainIgnoreCaseContainingAndSkillsSubdomainIgnoreCaseContainingAndProficiencyIgnoreCaseContaining(
    	    String empid, String skillname, String domain, String subdomain, String proficiency
    	);


    List<EmployeeSkill> findById_Empid(String empid);
   Optional<EmployeeSkill> findBySkills(Skills skill);
	    
	    Optional<EmployeeSkill> findBySkillsSkillname(String skillname);


    @Query("SELECT new com.example.dto.EmployeeDetailsDTO(es.skills.domain, es.skills.subdomain, " +
	            "es.skills.skillname, es.proficiency, es.certificationname, es.trainingdays) " +
	            "FROM EmployeeSkill es WHERE es.id.empid = :empid")
	    EmployeeDetailsDTO findUserSkills(@Param("empid") String empid);

    // Method to delete a skill by skill id and emp id
    void deleteById_EmpidAndId_Skillid(String empid, Integer skillid);


    List<EmployeeSkill> findById_Skillid(Integer skillId);

    Optional<EmployeeSkill> findById_EmpidAndId_Skillid(String empId, Integer skillId);
    Optional<EmployeeSkill> findById(EmpID id);

    
    @Transactional
    @Modifying
    @Query("UPDATE EmployeeSkill es SET es.reviewed = true WHERE es.id.empid = :empid AND es.id.skillid = :skillid")
    void reviewSkill(@Param("empid") String empid, @Param("skillid") Integer skillid);
    
    

    @Modifying
    @Query("UPDATE EmployeeSkill es SET es.proficiency = :proficiency, es.certificationname = :certificationname, es.trainingdays = :trainingdays WHERE es.id.empid = :empid AND es.id.skillid = :skillid")
    void updateSkill(@Param("empid") String empid, @Param("skillid") Integer skillid, @Param("proficiency") String proficiency, @Param("certificationname") String certificationname, @Param("trainingdays") int trainingdays);

 
}
