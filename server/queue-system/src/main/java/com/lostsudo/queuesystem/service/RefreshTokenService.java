package com.lostsudo.queuesystem.service;

import com.lostsudo.queuesystem.entity.RefreshToken;
import com.lostsudo.queuesystem.entity.User;
import com.lostsudo.queuesystem.repository.RefreshTokenRepository;
import com.lostsudo.queuesystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${app.jwt.refresh-token-expiration}")
    private long refreshTokenExpirationMs;

    public RefreshTokenRepository createRefreshTokenRepository(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpirationMs))
                .revoked(false)
                .build();

        return (RefreshTokenRepository) refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(rt -> {
                    if (rt.isRevoked() || rt.getExpiryDate().isBefore(Instant.now())) {
                        if (rt.isRevoked()) {
                            revokedAllUserTokens(rt.getUser().getId());
                        }
                        throw new RuntimeException("Token is expired");
                    }

                    return rt;
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));
    }

    public void revokeToken(RefreshToken token) {
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

    public void revokedAllUserTokens(UUID userId) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUserIdAndRevokedFalse(userId);
        tokens.forEach(rt -> rt.setRevoked(true));
        refreshTokenRepository.saveAll(tokens);
    }

    public void deleteByUserId(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

}
