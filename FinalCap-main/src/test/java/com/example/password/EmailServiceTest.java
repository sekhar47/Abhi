package com.example.password;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String text = "Test Message";

        // Act
        emailService.sendSimpleMessage(to, subject, text);

        // Assert
        verify(mailSenderMock).send(buildExpectedMessage(to, subject, text));
    }

    private SimpleMailMessage buildExpectedMessage(String to, String subject, String text) {
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(text);
        return expectedMessage;
    }
}
