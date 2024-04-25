package com.example.bidhub.jwt;

import com.example.bidhub.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public Optional<RefreshToken> findByMemId(String memId);
    public Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
