package com.example.emailverify;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 
import com.example.entity.User;
import com.example.repository.Userrepo;
import com.example.serviceauth.UserService;
 
import java.util.Date;
 
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
 
@Controller
public class UserAccountController {
 
    @Autowired
    private Userrepo userRepository;
 
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
 
    @Autowired
    private JavaMailSender javaMailSender; // Add this dependency
 
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @GetMapping("/registration")
    public String displayRegistration(Model model, User userEntity) {
        model.addAttribute("userEntity", userEntity);
        return "register";
    }
 
    @PostMapping("/registration")
    public String registerUser(Model model, User userEntity) {
        User existingUserByEmail = userRepository.findByEmpemail(userEntity.getEmpemail());
        User existingUserById = userRepository.findByEmpid(userEntity.getEmpid());
 
        if (existingUserByEmail != null) {
            model.addAttribute("message", "This email already exists!");
            return "register";
        } else if (existingUserById != null) {
            model.addAttribute("message", "This employee ID already exists!");
            return "register";
        } else {
        	userEntity.setEnable(true);
        	userEntity.setAccountNonLocked(true);
        	userEntity.setFailedAttempt(0);
        	userEntity.setLockTime(null);
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userRepository.save(userEntity);
            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);
            confirmationTokenRepository.save(confirmationToken);
 
            jakarta.mail.internet.MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "utf-8");
 
            try {
                helper.setTo(userEntity.getEmpemail());
                helper.setSubject("Complete Registration!");
            } catch (jakarta.mail.MessagingException e) {
                e.printStackTrace();
            }
 
            String emailBody = "<html>" +
                    "<head>" +
                    "<style>" +
                    "  body { font-family: 'Arial', sans-serif; background-color: #f4f4f4; text-align: center; margin: 0; padding: 0; }" +
                    "  .container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border: 2px solid #3498db; }" +
                    "  h2 { color: #3498db; font-size: 24px; margin-bottom: 10px; }" +
                    "  h3 { color: #3498db; font-size: 18px; margin-bottom: 20px; }" +
                    "  p { color: #555555; font-size: 16px; margin-bottom: 20px; }" +
                    "  a { display: inline-block; padding: 12px 24px; background-color: #3498db; color: #ffffff; text-decoration: none; border-radius: 5px; font-size: 16px; transition: background-color 0.3s ease; }" +
                    "  a:hover { background-color: #2980b9; }" +
                    "  .footer { margin-top: 20px; font-size: 14px; color: #888888; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h2>Welcome to TalenTrack!</h2>" +
                    "<h3>Your Skill Matrix System</h3>" +
                    "<p>To confirm your account, please click the link below:</p>" +
                    "<a href='http://localhost:8580/confirm-account?token=" + confirmationToken.getConfirmationToken() + "'>Confirm Your Account</a>" +
                    "<div class='footer'>Please do not reply to this automated email.</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
            
            try {
                helper.setText(emailBody, true);
                helper.setFrom("talentrackthbs@gmail.com"); // Set the sender email address
                javaMailSender.send(mailMessage);
            } catch (jakarta.mail.MessagingException e) {
                e.printStackTrace();
            }
 
            model.addAttribute("emailId", userEntity.getEmpemail());
            return "successfulRegistration";
        }
    }
 
 
 
 
 
    @RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
 
    public String confirmUserAccount(Model model, @RequestParam("token") String confirmationToken) {
 
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
        	User user = userRepository.findByEmpemail(token.getUserEntity().getEmpemail());
            user.setEnable(true);
            userRepository.save(user);
            return "accountVerified";
 
        } else {
            model.addAttribute("message", "The link is invalid or broken!");
 
            return "error";
 
        }
 
    }
 
 
    @Scheduled(fixedRate = 30000) // Run every 10 seconds for testing, you can change it to 60000 for 1 minute
 
    public void checkAndUpdateUserStatus() {
 
        System.out.println("Checking and updating user status...");
 
        Iterable<User> users = userRepository.findAll();
 
        for (User user : users) {
 
            System.out.println("Processing user: " + user.getEmpemail());
 
            if (user.isEnable()) {
 
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
 