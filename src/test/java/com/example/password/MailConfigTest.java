package com.example.password;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import javax.mail.Session;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

class MailConfigTest {

    @Test
    void testJavaMailSenderConfiguration() {
        // Create an instance of the MailConfig class
        MailConfig mailConfig = new MailConfig();

        // Get the JavaMailSender bean
        JavaMailSender javaMailSender = mailConfig.javaMailSender();

        // Verify that javaMailSender is an instance of JavaMailSenderImpl
        assertTrue(javaMailSender instanceof JavaMailSenderImpl);

        // Cast javaMailSender to JavaMailSenderImpl for further verification
        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl) javaMailSender;

        // Verify the host, port, username, and password configuration
        assertEquals("smtp.gmail.com", mailSenderImpl.getHost());
        assertEquals(587, mailSenderImpl.getPort());
        assertEquals("talentrackthbs@gmail.com", mailSenderImpl.getUsername());
        assertEquals("duamsvivibidjxhf", mailSenderImpl.getPassword());

        // Verify the mail properties
        Properties props = mailSenderImpl.getJavaMailProperties();
        assertEquals("smtp", props.getProperty("mail.transport.protocol"));
        assertEquals("true", props.getProperty("mail.smtp.auth"));
        assertEquals("true", props.getProperty("mail.smtp.starttls.enable"));
        assertEquals("true", props.getProperty("mail.debug"));
    }
}
