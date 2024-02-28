package com.example.manageemployee;


import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ManageEmployeesController {

    @Autowired
    private Manageempservice manageempService;

    
    @GetMapping("/manage-employees")
    public String deleteEmployee(Model model) {
        return "manage_employees";
    }
    
    @GetMapping("/deleteEmployees")
    public String manageEmployees(Model model) {
        List<User> users = manageempService.getAllUsers();
        model.addAttribute("users", users);
        return "deleteemployee";
    }

    @PostMapping("/delete-employees")
    public String deleteEmployees(@RequestParam(name = "empids", required = false) List<String> empIds) {
        if (empIds != null && !empIds.isEmpty()) {
        	manageempService.deleteUsersByEmpIds(empIds);
        }
        return "redirect:/deleteEmployees";
    }
    

    @GetMapping("/edit-privilege/{empid}")
    public String editPrivilege(@PathVariable("empid") String empid, Model model) {
        // Get user by empid
        User user = manageempService.getUserByEmpid(empid);
        if (user != null) {
            model.addAttribute("user", user);
            return "edit_privilege";
        } else {
            // Handle user not found scenario
            throw new RuntimeException("User not found with empid: " + empid);
        }
    }

    @PostMapping("/update-privilege")
    public String updatePrivilege(String empid, String privilege) {
    	manageempService.updateUserPrivilege(empid, privilege);
        return "redirect:/home"; // Redirect to home page after updating privilege
    }
    
    
    @GetMapping("/privilage")
    public String getAllEmployees(Model model) {
        List<User> employees = manageempService.getAllUsers();
        model.addAttribute("employees", employees);
        return "privilage";
    }

    @PostMapping("/updatePrivilege")
    public String updatePrivilege(@RequestParam("empid") String empid) {
    	manageempService.updatePrivilege(empid);
        return "redirect:/privilage";
    }

}
