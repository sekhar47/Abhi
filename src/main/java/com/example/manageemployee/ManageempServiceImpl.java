package com.example.manageemployee;
 
import com.example.entity.User;
import com.example.repository.Userrepo;
 
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
//... (existing imports)
 
@Service
@Transactional
public class ManageempServiceImpl implements Manageempservice {
 
    @Autowired
    private Userrepo userrepo;
 
    @Autowired
    private JavaMailSender javaMailSender;
 
    private static final String EMAIL_STYLE = "<style>"
            + "body { font-family: 'Arial', sans-serif; background-color: #f0f0f0; margin: 0; padding: 20px; }"
            + ".email-container { max-width: 600px; margin: 0 auto; }"
            + ".email-box { background-color: #ffffff; padding: 30px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); margin: 20px 0; animation: fadeIn 1s ease-in-out; }"
            + ".greeting { font-size: 22px; color: #333333; margin-bottom: 10px; }"
            + ".message { font-size: 18px; color: #444444; line-height: 1.6; margin-bottom: 20px; }"
            + ".closing { font-size: 16px; color: #555555; margin-top: 20px; }"
            + ".footer { margin-top: 20px; font-size: 14px; color: #777777; }"
            + "@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }"
            + "</style>"; 	
 
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
         String oldPrivilege = user.getPrivilage();
         user.setPrivilage(privilege);
         userrepo.save(user);
 
         sendPrivilegeUpdateEmail(user, oldPrivilege, privilege);
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
         String oldPrivilege = employee.getPrivilage();
 
         if (oldPrivilege.equals("USER")) {
             employee.setPrivilage("ADMIN");
         } else if (oldPrivilege.equals("ADMIN")) {
             employee.setPrivilage("SADMIN");
         }
         userrepo.save(employee);
 
         sendPrivilegeUpdateEmail(employee, oldPrivilege, employee.getPrivilage());
     }
}
 
@Override
public void promoteToSAdmin(String empid) {
     User employee = userrepo.findByEmpid(empid);
     if (employee != null) {
         String oldPrivilege = employee.getPrivilage();
         employee.setPrivilage("SADMIN");
         userrepo.save(employee);
 
         sendPrivilegeUpdateEmail(employee, oldPrivilege, employee.getPrivilage());
     }
}
 
@Override
public void revokePrivilege(String empid) {
     User employee = userrepo.findByEmpid(empid);
     if (employee != null) {
         String oldPrivilege = employee.getPrivilage();
         employee.setPrivilage("USER");
         userrepo.save(employee);
 
         sendPrivilegeUpdateEmail(employee, oldPrivilege, employee.getPrivilage());
     }
}
 
private void sendPrivilegeUpdateEmail(User user, String oldPrivilege, String newPrivilege) {
     String subject = "Privilege Update";
     String greeting = "Dear " + user.getName() + ",";
     String content = "Your privilege has been updated from " + oldPrivilege + " to " + newPrivilege + ".";
     String closing = "Thank you.";
     String footer = "This is an automated message. Please do not reply.";
 
     String emailContent = "<html>"
             + "<head>" + EMAIL_STYLE + "</head>"
             + "<body style='margin: 0;'>"
             + "<div class='email-container'>"
             + "<div class='email-box'>"
             + "<p class='greeting'>" + greeting + "</p>"
             + "<p class='message'>" + content + "</p>"
             + "<p class='closing'>" + closing + "</p>"
             + "<p class='footer'>" + footer + "</p>"
             + "</div>"
             + "</div>"
             + "</body>"
             + "</html>";
 
     MimeMessage message = javaMailSender.createMimeMessage();
     MimeMessageHelper helper = new MimeMessageHelper(message);
 
     try {
         helper.setTo(user.getEmpemail());
         helper.setSubject(subject);
         helper.setText(emailContent, true); // true indicates HTML content
         javaMailSender.send(message);
 
         // If successful, print a success message to the console
         System.out.println("Email sent successfully.");
     } catch (Exception e) {
         // If an exception occurs, print the exception details to the console
         e.printStackTrace();
         System.out.println("Error sending email: " + e.getMessage());
     }
}
}