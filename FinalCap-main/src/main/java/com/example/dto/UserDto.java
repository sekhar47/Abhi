package com.example.dto;

public class UserDto {
	 private String empid;
		private String name;
		private String empemail; 
		private String password;
		private String empmobile;
//		private String domain;
		private Boolean availability;
		private String privilage;
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
			return availability;
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
		public UserDto(String empid, String name, String empemail, String password, String empmobile,
				Boolean availability, String privilage) {
			super();
			this.empid = empid;
			this.name = name;
			this.empemail = empemail;
			this.password = password;
			this.empmobile = empmobile;
			this.availability = availability;
			this.privilage = privilage;
		}
		public UserDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		

	
	
	


	
	
	
	
	

}
