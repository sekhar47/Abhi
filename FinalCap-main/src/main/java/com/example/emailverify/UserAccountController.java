package com.example.emailverify;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.repository.Userrepo;
import com.example.serviceauth.UserService;


@Controller
public class UserAccountController {

    @Autowired
    private Userrepo userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailVerifyService emailService;

    @Autowired
    private TaskScheduler taskScheduler;

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	

	
    @GetMapping("/registration")
    public String displayRegistration(Model model, User userEntity) {
        model.addAttribute("userEntity", userEntity);
        return "register";
    }

   
    
    @PostMapping("/registration")
    public String registerUser(Model model, User userEntity) {
        User existingUser = userRepository.findByEmpemail(userEntity.getEmpemail());
        if (existingUser != null) {
            model.addAttribute("message", "This email already exists!");
            return "error";
        } else {
            // Encrypt the password before saving
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

            userRepository.save(userEntity);

            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEntity.getEmpemail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8580/confirm-account?token=" + confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);

            model.addAttribute("emailId", userEntity.getEmpemail());
            return "successfulRegistration";
        }
    }

    @RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
    public String confirmUserAccount(Model model, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
        	User user = userRepository.findByEmpemail(token.getUserEntity().getEmpemail());
            user.setEnabled(true);
            userRepository.save(user);
            return "accountVerified";
        } else {
            model.addAttribute("message", "The link is invalid or broken!");
            return "error";
        }
    }


    @Scheduled(fixedRate = 10000) // Run every 10 seconds for testing, you can change it to 60000 for 1 minute
    public void checkAndUpdateUserStatus() {
        System.out.println("Checking and updating user status...");

        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println("Processing user: " + user.getEmpemail());

            if (user.isEnabled()) {
                System.out.println("User is already enabled, skipping: " + user.getEmpemail());
                continue; // Skip if user is already enabled
            }

            ConfirmationToken token = confirmationTokenRepository.findByUserEntity(user);
            if (token != null && token.getExpirationDate().before(new Date())) {
                System.out.println("Token has expired for user: " + user.getEmpemail());
                
                // Token has expired, delete user
                userRepository.delete(user);
                confirmationTokenRepository.delete(token);

                System.out.println("User deleted due to expired token: " + user.getEmpemail());
            }
        }

        System.out.println("Check and update completed.");
    }

}
