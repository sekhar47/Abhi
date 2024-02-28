package com.example.skillvalidate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
public class NotificationController {

    @Autowired
    private EmployeeSkillValidateService employeeSkillValidateService;

    @GetMapping("/notifications")
    public String showNotifications(Model model, HttpSession session) {
        List<String> notifications = employeeSkillValidateService.getStoredNotifications();
        model.addAttribute("notifications", notifications);
        return "notificationPage"; // Assuming the name of your HTML file is notificationPage.html
    }
    
    @GetMapping("/notificationCount")
    @ResponseBody
    public int getNotificationCount() {
        List<String> notifications = employeeSkillValidateService.getStoredNotifications();
        return notifications.size();
    }
}
