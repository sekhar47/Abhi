package com.example.entity;



import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
//import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;




@Data

@Transactional
@Table(name = "EmployeeSkill")
@Entity
public class EmployeeSkill {
	
    
	@EmbeddedId
    private EmpID id;

		@ManyToOne
	    @JoinColumn(name = "empid", insertable = false, updatable = false)
	    private User user;

	 	@ManyToOne
	    @JoinColumn(name = "skillid", insertable = false, updatable = false)
	    private Skills skills;

	    private String proficiency;
	    private Boolean certified;
	    private String certificationname;
	    private boolean reviewed;
	    private int trainingdays;
		public EmpID getId() {
			return id;
		}
		public void setId(EmpID id) {
			this.id = id;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public Skills getSkills() {
			return skills;
		}
		public void setSkills(Skills skills) {
			this.skills = skills;
		}
		public String getProficiency() {
			return proficiency;
		}
		public void setProficiency(String proficiency) {
			this.proficiency = proficiency;
		}
		public Boolean getCertified() {
			return certified;
		}
		public void setCertified(Boolean certified) {
			this.certified = certified;
		}
		public String getCertificationname() {
			return certificationname;
		}
		public void setCertificationname(String certificationname) {
			this.certificationname = certificationname;
		}
		
		
		
		public boolean isReviewed() {
			return reviewed;
		}
		public void setReviewed(boolean reviewed) {
			this.reviewed = reviewed;
		}
		public int getTrainingdays() {
			return trainingdays;
		}
		public void setTrainingdays(int trainingdays) {
			this.trainingdays = trainingdays;
		}
		public EmployeeSkill(EmpID id, User user, Skills skills, String proficiency, Boolean certified,
				String certificationname, Boolean reviewed, int trainingdays) {
			super();
			this.id = id;
			this.user = user;
			this.skills = skills;
			this.proficiency = proficiency;
			this.certified = certified;
			this.certificationname = certificationname;
			this.reviewed = (reviewed != null) ? reviewed : Boolean.FALSE; // Set to Boolean.FALSE if null			
			this.trainingdays = trainingdays;
		}
		public EmployeeSkill() {
			super();
			// TODO Auto-generated constructor stub
		}
		
	    
	    
	    
		
	    
	    
		
		
		
		

}
