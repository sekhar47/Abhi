package com.example.entity;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.example.dto.EmployeeDetailsDTO;
import com.example.emailverify.ConfirmationToken;
import com.example.service.EmployeeService;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "Employee", uniqueConstraints = @UniqueConstraint(columnNames = "empemail"))
@Entity
public class User implements EmployeeService {
	

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String empid;
	private String name;
	private String empemail; 
	private String password;
	private String empmobile;
	private Boolean availability;
	private String privilage;
	
	

    @Column(name = "designation")
	private String designation;

	private String token;
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime tokenCreationDate;
	

//    private String rportingmanager;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<EmployeeSkill> employeeSkills;
	
	@OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE) // Cascade deletion from User to ConfirmationToken
	private Set<ConfirmationToken> confirmationToken;

	
    private boolean enable;
 
    private boolean isAccountNonLocked;
    
    public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public int getFailedAttempt() {
		return failedAttempt;
	}

	public void setFailedAttempt(int failedAttempt) {
		this.failedAttempt = failedAttempt;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	private int failedAttempt;
    
    private Date lockTime;
    
    
	
//	public String getRportingmanager() {
//		return rportingmanager;
//	}
//
//	public void setRportingmanager(String rportingmanager) {
//		this.rportingmanager = rportingmanager;
//	}

	public Set<ConfirmationToken> getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(Set<ConfirmationToken> confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Set<EmployeeSkill> getEmployeeSkills() {
		return employeeSkills;
	}

	public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
		this.employeeSkills = employeeSkills;
	}

	// Getter and setter methods for the "designation" field
	public String getDesignation() {
	    return designation;
	}

	public void setDesignation(String designation) {
	    this.designation = designation;
	}
	

	
	
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmpemail() {
		return empemail;
	}
	public void setEmpemail(String empemail) {
		this.empemail = empemail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmpmobile() {
		return empmobile;
	}
	public void setEmpmobile(String empmobile) {
		this.empmobile = empmobile;
	}

	

	public Boolean getAvailability() {
	    // Check if availability is null, return false if it is
	    return availability != null ? availability : false;
	}
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
	public String getPrivilage() {
		return privilage;
	}
	public void setPrivilage(String privilage) {
		this.privilage = privilage;
	}
	
	
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}

	public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}

	

	public User(String empid, String name, String empemail, String password, String empmobile, Boolean availability,
			String privilage, String designation, String token, LocalDateTime tokenCreationDate,
			Set<EmployeeSkill> employeeSkills) {
		super();
		this.empid = empid;
		this.name = name;
		this.empemail = empemail;
		this.password = password;
		this.empmobile = empmobile;
		this.availability = availability;
		this.privilage =  "USER";
		this.designation = designation;
		this.token = token;
		this.tokenCreationDate = tokenCreationDate;
//		this.profilePicture = profilePicture;
		this.employeeSkills = employeeSkills;
	}

	public User() {
		super();
		this.privilage =  "USER";
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<EmployeeDetailsDTO> getAllEmployeeDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeDetailsDTO> findByEmpid(String empid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeDetailsDTO> searchEmployees(String empid, String skillname, String domain, String subdomain,
			String proficiency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reviewSkill(String empid, Integer skillid) {
		// TODO Auto-generated method stub
		
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
