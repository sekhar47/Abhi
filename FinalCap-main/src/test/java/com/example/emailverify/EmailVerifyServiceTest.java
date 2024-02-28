package com.example.emailverify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailVerifyServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailVerifyService emailVerifyService;

    @Test
    public void testSendEmail() {
        // Arrange
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo("recipient@example.com");
        emailMessage.setSubject("Test Subject");
        emailMessage.setText("Test Content");

        // Act
        emailVerifyService.sendEmail(emailMessage);

        // Assert
        ArgumentCaptor<SimpleMailMessage> argumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(argumentCaptor.capture());

        SimpleMailMessage sentEmail = argumentCaptor.getValue();
        assertEquals("recipient@example.com", sentEmail.getTo()[0]);
        assertEquals("Test Subject", sentEmail.getSubject());
        assertEquals("Test Content", sentEmail.getText());
    }
}
