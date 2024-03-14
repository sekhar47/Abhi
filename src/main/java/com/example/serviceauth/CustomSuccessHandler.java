package com.example.serviceauth;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.example.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	
	@Autowired
	private UserService userService;

	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		var authourities = authentication.getAuthorities();
		var roles = authourities.stream().map(r -> r.getAuthority()).findFirst();
		
		  Object principal = authentication.getPrincipal();
		    if (principal instanceof CustomUserDetail) {
		        CustomUserDetail customUser = (CustomUserDetail) principal;
		        User user = customUser.getUser();
		        System.out.println("User is " + user);
		        if (user != null) {
		            userService.resetAttempt(user.getEmpemail());
		        }
		    }
		if (roles.orElse("").equals("ADMIN")) {
			response.sendRedirect("/admin-page");
		} else if (roles.orElse("").equals("USER")) {
			System.out.println("redirected to user-page");
			response.sendRedirect("/user-page");}
		
			else if (roles.orElse("").equals("SADMIN")) {
				response.sendRedirect("/superadmin-page");
		} else {
			response.sendRedirect("/error");
		}
		
		
		
	}


}

