package com.example.logincontroller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.UserDto;
import com.example.emailverify.ConfirmationToken;
import com.example.entity.User;
import com.example.serviceauth.UserService;
import com.example.skillvalidate.EmployeeSkillValidateService;



@Controller
public class LoginController {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private EmployeeSkillValidateService employeeSkillValidateService;
	
	
//	@GetMapping("/registration")
//	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
//		return "register";
//	}
//	
	
	
	
	
//	@PostMapping("/registration")
//	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
//		userService.save(userDto);
//		model.addAttribute("message", "Registered Successfuly!");
//		return "register";
//	}
	
	


	
	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) String error, Model model) {
	    if (error != null) {
	        model.addAttribute("errorMessage", "Incorrect username or password. Please try again.");
	    }
	    return "login";
	}

	@GetMapping("user-page")
	 @PreAuthorize("hasRole('USER')")
	public String userPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		List<String> notifications = employeeSkillValidateService.getStoredNotifications();
        model.addAttribute("notifications", notifications);
		return "userdashboard";
	}
	
	@GetMapping("admin-page")
	public String adminPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		return "admin";
	}

}
