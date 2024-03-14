package com.example.manageemployee;

import com.example.entity.User;
import com.example.repository.Userrepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.List;

@Controller
public class ManageEmployeesController {

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private Userrepo userRepository;
	
    @Autowired
    private Manageempservice manageempService;
    
    @GetMapping("/manage-employees")
    @PreAuthorize("hasAuthority('SADMIN')")
    public String deleteEmployee(Model model) {
        return "manage_employees";
    }

    @GetMapping("/deleteEmployees")
    @PreAuthorize("hasAuthority('SADMIN')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SADMIN')")
    public String getAllEmployees(Model model, Principal principal) {
        // Get the currently logged-in user's empid
        String loggedInEmpid = principal.getName(); // Assuming empid is stored as username
        
        // Check if the currently logged-in user is ADMIN or SADMIN
        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
                model.addAttribute("backUrl", "/admin-page");
            } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("SADMIN"))) {
                model.addAttribute("backUrl", "/superadmin-page");
            } else {
                // Handle other scenarios (if necessary)
            }
        }
        
        // Get employees list
        List<User> employees = manageempService.getAllUsers();
        
        // Exclude details of the currently logged-in SADMIN
        employees.removeIf(employee -> employee.getPrivilage().equals("SADMIN") && employee.getEmpid().equals(loggedInEmpid));
        
        model.addAttribute("employees", employees);
        
        return "privilage";
    }


    
    @PostMapping("/updatePrivilege")
    public String updatePrivilege(@RequestParam("empid") String empid) {
    	manageempService.updatePrivilege(empid);
        return "redirect:/privilage";
    }
    
    @PostMapping("/promoteToSAdmin")
    @PreAuthorize("hasAuthority('SADMIN')")
    public String promoteToSAdmin(@RequestParam("empid") String empid) {
        manageempService.promoteToSAdmin(empid);
        return "redirect:/privilage";
    }

    @PostMapping("/revokePrivilege")
    @PreAuthorize("hasAuthority('SADMIN')")
    public String revokePrivilege(@RequestParam("empid") String empid) {
        manageempService.revokePrivilege(empid);
        return "redirect:/privilage";
    }
    
  

}
