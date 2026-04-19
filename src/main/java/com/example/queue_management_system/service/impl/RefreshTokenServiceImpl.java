package com.example.queue_management_system.service.impl;

import com.example.queue_management_system.domain.RefreshToken;
import com.example.queue_management_system.repository.RefreshTokenRepository;
import com.example.queue_management_system.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void createRefreshToken(String token, String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .email(email)
                .isRevoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken getRefreshToken(String token) {
        RefreshToken refreshToken =  refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        return RefreshToken.builder()
                .id(refreshToken.getId())
                .token(refreshToken.getToken())
                .email(refreshToken.getEmail())
                .isRevoked(refreshToken.isRevoked())
                .issuedAt(refreshToken.getIssuedAt())
                .expiredAt(refreshToken.getExpiredAt())
                .build();
    }

    @Override
    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void deleteRefreshToken(String token) {
        RefreshToken refreshToken =  refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshTokenRepository.delete(refreshToken);
    }
}
