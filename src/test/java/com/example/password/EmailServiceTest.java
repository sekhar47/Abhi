package com.example.password;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSenderMock;

    @InjectMocks
    private EmailService emailService;

    @Test
    void testSendSimpleMessage() {
        // Arrange
        String to = "basulm18@gmail.com";
        String subject = "Skill Review";
        String text = "Test Message";

        // Act
        emailService.sendSimpleMessage(to, subject, text);

        // Assert
        verify(mailSenderMock).send((jakarta.mail.internet.MimeMessage) any(MimeMessage.class));
    }
}