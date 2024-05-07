package com.ssafy.springbootapi.global.auth.jwt.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(UUID userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
