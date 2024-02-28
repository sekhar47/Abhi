//package com.example.skillvalidate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//
//@Service
//public class NotificationService {
//
//    public void sendNotification(String message, HttpServletResponse response) {
//        // Set notification message in a cookie
//        Cookie notificationCookie = new Cookie("notification", message);
//        notificationCookie.setMaxAge(86400); // Set cookie expiration time (in seconds), e.g., 24 hours
//        notificationCookie.setPath("/"); // Set cookie path to root
//        response.addCookie(notificationCookie);
//    }
//}
