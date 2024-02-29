package com.example.password;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder; // Import PasswordEncoder
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.Userrepo;

@Service
public class UserService {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    @Autowired
    private Userrepo userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    public String forgotPassword(String email) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmpemail(email));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = generateToken();
            user.setToken(token);
            user.setTokenCreationDate(LocalDateTime.now());
            userRepository.save(user);

            sendResetLink(email, token);
            return "Reset link sent to your email.";
        } else {
            return "Invalid email, please enter your email linked to your THBS account ";
        }
    }

    public String validateResetToken(String token) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByToken(token));

        if (userOptional.isPresent()) {
            LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

            if (!isTokenExpired(tokenCreationDate)) {
                return "Valid token";
            } else {
                return "Token expired. please enter your email and reset the password again.";
            }
        } else {
            return "Invalid token. please check your link, TRY CLICKING ON THE LINK AGAIN";
        }
    }

    public String resetPassword(String token, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match. Recheck again";
        }

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByToken(token));

        if (userOptional.isPresent()) {
            LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

            if (!isTokenExpired(tokenCreationDate)) {
                User user = userOptional.get();
                user.setPassword(passwordEncoder.encode(password)); // Encrypt password before saving
                user.setToken(null);
                user.setTokenCreationDate(null);
                userRepository.save(user);

                return "Your password has been successfully updated.- TALENTRACK";
            } else {
                return "Token expired. please enter your email and reset the password again";
            }
        } else {
            return "Invalid token. please check your link, TRY CLICKING ON THE LINK AGAIN";
        }
    }

    private void sendResetLink(String email, String token) {
        String resetLink = "http://13.126.85.125:8580/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Link");
        message.setText("Click on the link to reset your password for your TALENTRACK account: " + resetLink);

        mailSender.send(message);
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();
        return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);
        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}
