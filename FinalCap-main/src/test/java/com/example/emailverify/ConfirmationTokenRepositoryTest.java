package com.example.emailverify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.entity.User;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenRepositoryTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository1;

    @Test
    void testFindExpiredTokens() {
        // Given
        Timestamp expirationDate = new Timestamp(System.currentTimeMillis());
        List<ConfirmationToken> tokenList = new ArrayList<>();
        ConfirmationToken token1 = new ConfirmationToken();
        ConfirmationToken token2 = new ConfirmationToken();
        tokenList.add(token1);
        tokenList.add(token2);

        // Mocking the behavior of the repository
        when(confirmationTokenRepository1.findExpiredTokens(expirationDate)).thenReturn(tokenList);

        // When
        List<ConfirmationToken> result = confirmationTokenRepository1.findExpiredTokens(expirationDate);

        // Then
        assertEquals(tokenList, result);
        verify(confirmationTokenRepository1).findExpiredTokens(expirationDate);
    }

 // Existing imports...
        @Mock
        private ConfirmationTokenRepository confirmationTokenRepository;

        @Test
        void testFindByConfirmationToken() {
            // Given
            String tokenValue = "sampleToken";
            ConfirmationToken token = new ConfirmationToken();
            token.setConfirmationToken(tokenValue);

            // Mocking the behavior of the repository
            when(confirmationTokenRepository1.findByConfirmationToken(tokenValue)).thenReturn(token);

            // When
            ConfirmationToken result = confirmationTokenRepository1.findByConfirmationToken(tokenValue);

            // Then
            assertEquals(token, result);
            verify(confirmationTokenRepository1).findByConfirmationToken(tokenValue);
        }

        @Test
        void testFindByUserEntity() {
            // Given
            User user = new User();
            ConfirmationToken token = new ConfirmationToken();
            token.setUserEntity(user);

            // Mocking the behavior of the repository
            when(confirmationTokenRepository1.findByUserEntity(user)).thenReturn(token);

            // When
            ConfirmationToken result = confirmationTokenRepository1.findByUserEntity(user);

            // Then
            assertEquals(token, result);
            verify(confirmationTokenRepository1).findByUserEntity(user);
        }

        @Test
        void testDeleteByUserEntity() {
            // Given
            User user = new User();

            // When
            confirmationTokenRepository1.deleteByUserEntity(user);

            // Then
            verify(confirmationTokenRepository1).deleteByUserEntity(user);
        }

        @Test
        void testSave() {
            // Given
            ConfirmationToken token = new ConfirmationToken();

            // Mocking the behavior of the repository
            when(confirmationTokenRepository1.save(token)).thenReturn(token);

            // When
            ConfirmationToken result = confirmationTokenRepository1.save(token);

            // Then
            assertEquals(token, result);
            verify(confirmationTokenRepository1).save(token);
        }

        @Test
        void testDeleteById() {
            // Given
            long tokenId = 1L;

            // When
            confirmationTokenRepository1.deleteById(tokenId);

            // Then
            verify(confirmationTokenRepository1).deleteById(tokenId);
        }

        @Test
        void testFindById() {
            // Given
            long tokenId = 1L;
            ConfirmationToken token = new ConfirmationToken();

            // Mocking the behavior of the repository
            when(confirmationTokenRepository1.findById(tokenId)).thenReturn(Optional.of(token));

            // When
            Optional<ConfirmationToken> result = confirmationTokenRepository1.findById(tokenId);

            // Then
            assertEquals(Optional.of(token), result);
            verify(confirmationTokenRepository1).findById(tokenId);
        }

        @Test
        void testFindAll() {
            // Given
            List<ConfirmationToken> tokenList = new ArrayList<>();

            // Mocking the behavior of the repository
            when(confirmationTokenRepository1.findAll()).thenReturn(tokenList);

            // When
            Iterable<ConfirmationToken> result = confirmationTokenRepository1.findAll();

            // Then
            assertEquals(tokenList, result);
            verify(confirmationTokenRepository1).findAll();
        }
        

}
