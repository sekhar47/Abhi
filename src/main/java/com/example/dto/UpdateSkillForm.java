package com.example.dto;

public class UpdateSkillForm {
    private String domain;
    private String subdomain;
    private String skillName;
    private String newSkillName;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getNewSkillName() {
		return newSkillName;
	}
	public void setNewSkillName(String newSkillName) {
		this.newSkillName = newSkillName;
	}
	public UpdateSkillForm(String domain, String subdomain, String skillName, String newSkillName) {
		super();
		this.domain = domain;
		this.subdomain = subdomain;
		this.skillName = skillName;
		this.newSkillName = newSkillName;
	}
	public UpdateSkillForm() {
		super();
		// TODO Auto-generated constructor stub
	}

}

