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

    private Key getAccessTokenSigningKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (accessTokenExpiry * 1000)))
                .signWith(getAccessTokenSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getAccessTokenSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValidToken(String token, UserDetails userDetails) {
        final String email = extractEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !tokenIsExpired(token));
    }

    private boolean tokenIsExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getAccessTokenSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
