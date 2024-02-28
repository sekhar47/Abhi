package com.example.manageemployee;

import com.example.entity.User;

import java.util.List;

public interface Manageempservice {

    
    List<User> getAllUsers();
    void deleteUsersByEmpIds(List<String> empIds);
    User getUserByEmail(String email);
    void updateUserPrivilege(String empid, String privilege);
	User getUserByEmpid(String empid);
    void updatePrivilege(String empid);

}
