package com.example.entity;


import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "Skills")
@Entity
public class Skills {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer skillid;
	private String skillname;
	private String subdomain; 
	private String domain;
	
	   @OneToMany(mappedBy = "skills", cascade = CascadeType.ALL)
	    private Set<EmployeeSkill> employeeSkills;
	   
	   
	public Set<EmployeeSkill> getEmployeeSkills() {
		return employeeSkills;
	}
	public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
		this.employeeSkills = employeeSkills;
	}
	public Integer getSkillid() {
		return skillid;
	}
	public void setSkillid(Integer skillid) {
		this.skillid = skillid;
	}
	public String getSkillname() {
		return skillname;
	}
	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}
	public String getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
	
	public Skills(Integer skillid, String skillname, String subdomain, String domain,
			Set<EmployeeSkill> employeeSkills) {
		super();
		this.skillid = skillid;
		this.skillname = skillname;
		this.subdomain = subdomain;
		this.domain = domain;
		this.employeeSkills = employeeSkills;
	}
	public Skills() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
