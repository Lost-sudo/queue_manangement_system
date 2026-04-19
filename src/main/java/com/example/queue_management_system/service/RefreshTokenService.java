package com.example.queue_management_system.service;

import com.example.queue_management_system.domain.RefreshToken;

public interface RefreshTokenService {
    void createRefreshToken(String refreshToken, String email);
    RefreshToken getRefreshToken (String refreshToken);
    void revokeToken(String refreshToken);
    void deleteRefreshToken(String token);
}
