// src/main/java/com/tr/rms/security/token/RefreshTokenRepository.java
package com.trbtree.service.rbac.repository;

import com.trbtree.service.security.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    /** Find token string */
    Optional<RefreshToken> findByToken(String token);

    /** Revoke a token (GDPR audit) */
    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.revokedAt = CURRENT_TIMESTAMP WHERE rt.token = :token")
    void revokeByToken(String token);
}