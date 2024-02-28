package com.example.service;

import com.example.dto.EmployeeDetailsDTO;
import com.example.entity.EmployeeSkill;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    List<EmployeeDetailsDTO> getAllEmployeeDetails();
    
//    sesrch and filter module

    List<EmployeeDetailsDTO> findByEmpid(String empid);

//    filter
    List<EmployeeDetailsDTO> searchEmployees(String empid, String skillname, String domain, String subdomain, String proficiency);

   

	void reviewSkill(String empid, Integer skillid);

	String getname();

	String getempid();

	String getempemail();


}

