package com.example.queue_management_system.controller;

import com.example.queue_management_system.dto.*;
import com.example.queue_management_system.security.RefreshTokenCookieService;
import com.example.queue_management_system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RefreshTokenCookieService refreshTokenCookieService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        AuthServiceResponse authServiceResponse = userService.login(request);

        refreshTokenCookieService.addRefreshTokenCookie(response, authServiceResponse.getRefreshToken());

        return AuthResponse.builder()
                .token(authServiceResponse.getToken())
                .build();
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        AuthServiceResponse authServiceResponse =  userService.refreshToken(request);

        refreshTokenCookieService.clearRefreshTokenCookie(response);
        refreshTokenCookieService.addRefreshTokenCookie(response, authServiceResponse.getRefreshToken());

        return AuthResponse.builder()
                .token(authServiceResponse.getToken())
                .build();
    }
}
