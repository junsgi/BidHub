package com.example.bidhub.jwt;

import com.example.bidhub.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repository;
    public RefreshToken findByRefreshToken(String refreshToken) {
        return repository.findByRefreshToken(refreshToken)
                .orElseThrow(IllegalArgumentException::new);
    }

    public RefreshToken findByMemId(String memId) {
        return repository.findByMemId(memId)
                .orElseGet(()->new RefreshToken(null, memId, null));
    }
    public RefreshToken save(RefreshToken refreshToken) {
        return repository.save(refreshToken);
    }
}
