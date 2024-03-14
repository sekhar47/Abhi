package com.example.serviceauth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.entity.User;
import com.example.repository.Userrepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserService userService;
	
	@Autowired
	private Userrepo userrepo;
	
	   @Autowired
	    private HttpSession session;
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String email = request.getParameter("username");
		System.out.println("username ------" + email);
		User user = userrepo.findByEmpid(email);
		System.out.println("empemailid " + user);
		if(user!=null) {
			System.out.println("Inside the not equal to null......................");
			if(user.isEnable()) {
				System.out.println("Inside the isenable");
				if(user.isAccountNonLocked()) {
					System.out.println("Inside the AccountNonLocked");
					if(user.getFailedAttempt() < UserServiceImpl.ATTEMPT_TIME - 1) {
						System.out.println("failedAttempt" + user.getFailedAttempt());
						System.out.println("time attempts - "+ UserServiceImpl.ATTEMPT_TIME);
						userService.increaseFailedAttempt(user);
						System.out.println("sdfsdfsdfsdfsdfs");
					}else {
						System.out.println("outsiddeeeeee...........");
						userService.lock(user);
						System.out.println("inside the else lock");
						exception=new LockedException("Your Account is locked!! failed attempt 3 times");	
						super.setDefaultFailureUrl("/login?error");
					}
				}else if(!user.isAccountNonLocked()) {
					System.out.println("Inside the else if");
					if (userService.unlockAccountTimeExpired(user)) {
						System.out.println("unlockaccount ...........");
						exception=new LockedException("Account is unlocked! Please try to login");
					}else {
						exception=new LockedException("Account is locked! Please try after sometime");
					}
				}
			}else {
				exception=new LockedException("Account is inactive.. verify accound");
			}
		}
		
		System.out.println(exception.getMessage());
		session.setAttribute("loginError", exception.getMessage());
		  
		
	
		super.onAuthenticationFailure(request, response, exception);
	}
	
	
}
