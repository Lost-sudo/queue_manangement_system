package com.example.queue_management_system.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.access_token_secret}")
    private String accessTokenSecret;

    @Value("${jwt.access_token_expiry}")
    private Long accessTokenExpiry;

    @Value("${jwt.refresh_token_secret}")
    private String refreshTokenSecret;

    @Value("${jwt.refresh_token_expiry}")
    private Long refreshTokenExpiry;

    private Key getAccessTokenSigningKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    private Key getRefreshTokenSigningKey() {
        return Keys.hmacShaKeyFor(refreshTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (accessTokenExpiry * 1000)))
                .signWith(getAccessTokenSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (refreshTokenExpiry * 1000)))
                .signWith(getRefreshTokenSigningKey())
                .compact();
    }

    public String extractEmailFromToken(String type, String token) {
        if (type.equals("refresh_token")) {
            return Jwts.parserBuilder()
                    .setSigningKey(getRefreshTokenSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } else {
            return Jwts.parserBuilder()
                    .setSigningKey(getAccessTokenSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
    }

    public boolean isValidToken(String type, String token, UserDetails userDetails) {
        final String email = extractEmailFromToken(type, token);
        return (email.equals(userDetails.getUsername()) && !tokenIsExpired(type, token));
    }

    private boolean tokenIsExpired(String type, String token) {
        return extractExpiration(type, token).before(new Date());
    }

    private Date extractExpiration(String type, String token) {
        if (type.equals("refresh_token")) {
            return Jwts.parserBuilder()
                    .setSigningKey(getRefreshTokenSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
        } else {
            return Jwts.parserBuilder()
                    .setSigningKey(getAccessTokenSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
        }
    }
}
