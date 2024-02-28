package com.example.skillvalidate;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
 
import com.example.entity.EmployeeSkill;
import com.example.entity.Skills;
import com.example.entity.User;
import com.example.repository.Empskillrepo;
import com.example.repository.Skillrepo;
import com.example.repository.Userrepo;
 
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
 
@Service
public class EmployeeSkillValidateService {
 
    @Autowired
    private Empskillrepo empskillrepo;
 
    @Autowired
    private JavaMailSender javaMailSender;
 
    @Autowired
    private Userrepo employeeRepository;
 
    @Autowired
    private Skillrepo skillRepository;
 
    @Value("${spring.mail.username}")
    private String mailSenderAddress;
 
    @Autowired
    private HttpSession session;
 
    // Scheduled executor service for delayed email sending
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
 
    // List to store pending email tasks
    private final List<ApprovalNotificationTask> pendingEmailTasks = new ArrayList<>();
 
    public List<EmployeeSkill> getAllPendingAndRejectedEmployeeSkills() {
        return empskillrepo.findByReviewedFalse();
    }
 
    public void reviewEmployeeSkill(String empid, Integer skillid, boolean approved) {
        String action = approved ? "reviewed" : "rejected";
 
        if (approved) {
            empskillrepo.reviewSkill(empid, skillid);
        } else {
            empskillrepo.deleteByEmpidAndSkillid(empid, skillid);
        }
 
        // Fetch employee details from the 'employee' table based on empid
        User employee = employeeRepository.findByEmpid(empid);
 
        // Fetch skill name based on skillid
        String skillName = fetchSkillNameById(skillid);
 
        // Include skill name in the notification
        String notification = "Dear Employee with empID - " + empid +
                " Your skill '" + skillName + "' with ID " + skillid + " has been " + action + ".";
 
        storeNotificationInSession(notification);
 
        // Schedule email sending task
        scheduleEmailSendingTask(employee, skillid, skillName, action);
 
        // Update notification count in session
        updateNotificationCount();
    }
 
    private void scheduleEmailSendingTask(User employee, Integer skillid, String skillname, String action) {
        // Check if there is a pending task for this employee
        ApprovalNotificationTask existingTask = getPendingTaskForEmployee(employee.getEmpid());
 
        if (existingTask != null) {
            // Add the current skill details to the existing task
            existingTask.addSkillDetails(skillid, skillname, action);
        } else {
            // Create a new task and add it to the pending tasks list
            ApprovalNotificationTask newTask = new ApprovalNotificationTask(employee, skillid, skillname, action);
            pendingEmailTasks.add(newTask);
 
            // Schedule the new task to run after a delay of 1 minute
            executorService.schedule(newTask, 1, TimeUnit.MINUTES);
        }
    }
 
    private ApprovalNotificationTask getPendingTaskForEmployee(String empid) {
        for (ApprovalNotificationTask task : pendingEmailTasks) {
            if (task.getEmployeeId().equals(empid)) {
                return task;
            }
        }
        return null;
    }
 
    private void sendNotificationEmail(User employee, ApprovalNotificationTask task) {
        String subject = "Skill Approval/Rejection Notification";
 
        // Construct the table structure with enhanced styling in HTML
        String tableHeader = "<thead style='background-color: #3498db; color: white;'>" +
                "<tr>" +
                "<th style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>Employee ID</th>" +
                "<th style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>Employee Name</th>" +
                "<th style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>Skill Name</th>" +
                "<th style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>Skill ID</th>" +
                "<th style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>Action</th>" +
                "</tr>" +
                "</thead>";
 
        String tableBody = "<tbody style='background-color: #f2f2f2;'>";
 
        for (SkillApprovalDetails details : task.getSkillDetailsList()) {
            tableBody += "<tr>" +
                    "<td style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>" + employee.getEmpid() + "</td>" +
                    "<td style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>" + employee.getName() + "</td>" +
                    "<td style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>" + details.getSkillName() + "</td>" +
                    "<td style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>" + details.getSkillId() + "</td>" +
                    "<td style='border: 1px solid #dddddd; text-align: left; padding: 12px;'>" + details.getAction() + "</td>" +
                    "</tr>";
        }
 
        tableBody += "</tbody>";
 
        String table = "<table style='width: 100%; border-collapse: collapse; margin-top: 15px;'>" +
                tableHeader +
                tableBody +
                "</table>";
 
        // Construct the email body
        String body = "Dear Employee " + employee.getName() + " - " + employee.getEmpid() + ",<br>" +
                "Your skills have been reviewed by the admin. Details are as follows:<br><br>" + table;
 
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
 
        try {
            helper.setFrom(mailSenderAddress);
            helper.setTo(employee.getEmpemail());
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
 
    private void storeNotificationInSession(String notification) {
        @SuppressWarnings("unchecked")
        List<String> notifications = (List<String>) session.getAttribute("notifications");
        if (notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(0, notification);
        session.setAttribute("notifications", notifications);
    }
 
    public List<String> getStoredNotifications() {
        return (List<String>) session.getAttribute("notifications");
    }
 
    private void updateNotificationCount() {
        @SuppressWarnings("unchecked")
        Integer count = (Integer) session.getAttribute("notificationCount");
        if (count == null) {
            count = 0;
        }
        count++;
        session.setAttribute("notificationCount", count);
    }
 
// New method to fetch skill name by skillid
    private String fetchSkillNameById(Integer skillid) {
        return skillRepository.findById(skillid)
                .map(Skills::getSkillname)
                .orElse("Skill Name Not Found");
    }
 
 
    // Helper class to represent details of a skill approval
    private static class SkillApprovalDetails {
        private final Integer skillId;
        private final String skillName;
        private final String action;
 
        public SkillApprovalDetails(Integer skillId, String skillName, String action) {
            this.skillId = skillId;
            this.skillName = skillName;
            this.action = action;
        }
 
        public Integer getSkillId() {
            return skillId;
        }
 
        public String getSkillName() {
            return skillName;
        }
 
        public String getAction() {
            return action;
        }
    }
 
    // Helper class to represent a pending email task for skill approvals
    private class ApprovalNotificationTask implements Runnable {
        private final String employeeId;
        private final List<SkillApprovalDetails> skillDetailsList;
 
        public ApprovalNotificationTask(User employee, Integer skillId, String skillName, String action) {
            this.employeeId = employee.getEmpid();
            this.skillDetailsList = new ArrayList<>();
            addSkillDetails(skillId, skillName, action);
        }
 
        public void addSkillDetails(Integer skillId, String skillName, String action) {
            SkillApprovalDetails details = new SkillApprovalDetails(skillId, skillName, action);
            skillDetailsList.add(details);
        }
 
        public String getEmployeeId() {
            return employeeId;
        }
 
        public List<SkillApprovalDetails> getSkillDetailsList() {
            return skillDetailsList;
        }
 
        @Override
        public void run() {
            // Execute the email sending task
            User employee = employeeRepository.findByEmpid(employeeId);
            sendNotificationEmail(employee, this);
 
            // Remove the completed task from the pending tasks list
            pendingEmailTasks.remove(this);
        }
    }
}