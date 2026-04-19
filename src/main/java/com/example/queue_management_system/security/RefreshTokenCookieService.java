package com.example.queue_management_system.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class RefreshTokenCookieService {
    private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        try {
            Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, URLEncoder.encode(refreshToken, StandardCharsets.UTF_8));
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(604800);

            response.addCookie(cookie);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding refresh token", e);
        }
    }

    public void clearRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}
