package com.example.manageemployee;


import com.example.entity.User;
import com.example.repository.Userrepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class ManageempServiceImpl implements Manageempservice {

    @Autowired
    private Userrepo userrepo;

    
  
    @Override
    public List<User> getAllUsers() {
        return userrepo.findAll();
    }
    
   
    @Override
    public void deleteUsersByEmpIds(List<String> empIds) {
        for (String empId : empIds) {
            userrepo.deleteById(empId);
        }
    }
    
    @Override
    public User getUserByEmail(String email) {
        return userrepo.findByEmpemail(email);
    }
  
    
    
    
    
    @Override
    public void updateUserPrivilege(String empid, String privilege) {
        User user = userrepo.findByEmpid(empid);
        if (user != null) {
            user.setPrivilage(privilege);
            userrepo.save(user);
        } else {
            // Handle user not found scenario
            throw new RuntimeException("User not found with empid: " + empid);
        }
    }


    @Override
    public User getUserByEmpid(String empid) {
        return userrepo.findByEmpid(empid);
    }


    
    @Override
    public void updatePrivilege(String empid) {
        User employee = userrepo.findByEmpid(empid);
        if (employee != null) {
            employee.setPrivilage("ADMIN");
            userrepo.save(employee);
        }
    }
    
}
