package com.example.emailverify;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.example.emailverify.ConfirmationToken;
import com.example.entity.User;

class ConfirmationTokenTest {

    @Test
    void testTokenCreation() {
        User user = new User(); // You might need to set up your User entity as needed for testing
        ConfirmationToken token = new ConfirmationToken(user);

        assertNotNull(token);
        assertNotNull(token.getConfirmationToken());
        assertNotNull(token.getCreatedDate());
        assertNotNull(token.getExpirationDate());
        assertNotNull(token.getUserEntity());
        assertEquals(user, token.getUserEntity());
    }



 
    @Test
    void testSettersAndGetters() {
        ConfirmationToken token = new ConfirmationToken();
        User user = new User();

        token.setTokenid(123);
        token.setConfirmationToken("testToken");
        token.setCreatedDate(new Date());
        token.setExpirationDate(new Date());
        token.setUserEntity(user);

        assertEquals(123, token.getTokenid());
        assertEquals("testToken", token.getConfirmationToken());
        assertNotNull(token.getCreatedDate());
        assertNotNull(token.getExpirationDate());
        assertEquals(user, token.getUserEntity());
    }

    @Test
    void testSetUserEntity() {
        ConfirmationToken token = new ConfirmationToken();
        User user1 = new User();
        User user2 = new User();

        token.setUserEntity(user1);
        assertEquals(user1, token.getUserEntity());

        token.setUserEntity(user2);
        assertEquals(user2, token.getUserEntity());
    }

    @Test
    void testConstructorWithUser() {
        User user = new User();
        ConfirmationToken token = new ConfirmationToken(user);

        assertNotNull(token);
        assertNotNull(token.getConfirmationToken());
        assertNotNull(token.getCreatedDate());
        assertNotNull(token.getExpirationDate());
        assertEquals(user, token.getUserEntity());
    }

 

    @Test
    void testSetExpirationDate() {
        ConfirmationToken token = new ConfirmationToken();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Date expirationDate = java.sql.Timestamp.valueOf(currentDateTime.plus(10, ChronoUnit.MINUTES));

        token.setExpirationDate(expirationDate);

        assertEquals(expirationDate, token.getExpirationDate());
    }

}

