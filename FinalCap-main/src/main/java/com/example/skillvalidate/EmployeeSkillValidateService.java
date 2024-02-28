package com.example.skillvalidate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.entity.EmployeeSkill;
import com.example.entity.Skills;
import com.example.entity.User;
import com.example.repository.Empskillrepo;
import com.example.repository.Skillrepo;
import com.example.repository.Userrepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmployeeSkillValidateService {

    @Autowired
    private Empskillrepo empskillrepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Userrepo employeeRepository;

    @Autowired
    private Skillrepo skillRepository; // Autowire Skillrepo

    @Value("${spring.mail.username}")
    private String mailSenderAddress;

    public List<EmployeeSkill> getAllEmployeeSkills() {
        return empskillrepo.findAll();
    }

    public void reviewEmployeeSkill(String empid, Integer skillid) {
        empskillrepo.reviewSkill(empid, skillid);

        // Fetch employee details from the 'employee' table based on empid
        User employee = employeeRepository.findByEmpid(empid);

        // Fetch skill name based on skillid
        String skillName = fetchSkillNameById(skillid);

        // Include skill name in the notification
        String notification = "Dear Employee with empID - " + empid +
                " Your skill '" + skillName + "' with ID " + skillid + " has been reviewed.";
        
        storeNotificationInSession(notification);

        // Send email to the employee
        sendReviewNotificationEmail(employee, skillid, skillName);
    }


    private void sendReviewNotificationEmail(User employee, Integer skillid, String skillname) {
        String subject = "Skill Review Notification";
        String body = "Dear Employee " + employee.getName() + " - " + employee.getEmpid() + ", your skill '"
                + skillname + "' with ID " + skillid + " has been reviewed by the admin.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSenderAddress);
        message.setTo(employee.getEmpemail());
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    private void storeNotificationInSession(String notification) {
        @SuppressWarnings("unchecked")
        List<String> notifications = (List<String>) getRequest().getSession().getAttribute("notifications");
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(0, notification);
        getRequest().getSession().setAttribute("notifications", notifications);
    }

    public List<String> getStoredNotifications() {
        return (List<String>) getRequest().getSession().getAttribute("notifications");
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    // New method to fetch skill name by skillid
    private String fetchSkillNameById(Integer skillid) {
        // Implement the logic to fetch skill name by skillid from the skill repository
         example : return skillRepository.findById(skillid).map(Skills::getSkillname).orElse("skill name not found");
    }
}
