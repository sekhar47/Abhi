package com.example.emailverify;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

    // Existing methods...

    @Query("SELECT c FROM ConfirmationToken c WHERE c.createdDate < :expirationDate")
    List<ConfirmationToken> findExpiredTokens(@Param("expirationDate") Timestamp expirationDate);

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    ConfirmationToken findByUserEntity(User userEntity);

    @Query("DELETE FROM ConfirmationToken c WHERE c.userEntity = :userEntity")
    void deleteByUserEntity(@Param("userEntity") User userEntity);
}
