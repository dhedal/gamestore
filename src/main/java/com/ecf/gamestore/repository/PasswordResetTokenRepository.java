package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    public PasswordResetToken findByUser(GSUser user);
}