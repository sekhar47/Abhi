package com.example.password;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        String response = userService.forgotPassword(email);

        if (response.equals("Reset link sent to your email.")) {
            model.addAttribute("message", response);
        } else {
            model.addAttribute("error", response);
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        String response = userService.validateResetToken(token);

        if (response.equals("Valid token")) {
            model.addAttribute("token", token);
            return "reset-password";
        } else {
            model.addAttribute("error", response);
            return "error";
        }
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password,
                                @RequestParam String confirmPassword, Model model) {
        String response = userService.resetPassword(token, password, confirmPassword);

        if (response.equals("Your password has been successfully updated.")) {
            model.addAttribute("message", response);
        } else {
            model.addAttribute("error", response);
        }
        return "reset-password";
    }
}
