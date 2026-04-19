package com.example.queue_management_system.service.impl;

import com.example.queue_management_system.domain.Role;
import com.example.queue_management_system.domain.User;
import com.example.queue_management_system.dto.*;
import com.example.queue_management_system.mapper.UserMapper;
import com.example.queue_management_system.repository.UserRepository;
import com.example.queue_management_system.security.JwtService;
import com.example.queue_management_system.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResponse register(UserRegisterRequest request) {
        String email = request.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user;

        if (request.getPassword().equals(request.getConfirmPassword())) {
            user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
        } else {
            throw new RuntimeException("Passwords do not match");
        }

        User savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public AuthServiceResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return AuthServiceResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthServiceResponse refreshToken(HttpServletRequest request) {
        final String TYPE =  "refresh_token";
        String encodedRefreshToken = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(TYPE))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new RuntimeException("Cookie not found"));
        try {
            String refreshToken = URLDecoder.decode(encodedRefreshToken, StandardCharsets.UTF_8);
            String email = jwtService.extractEmailFromToken(TYPE, refreshToken);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserDetails userDetails = org.springframework.security.core.userdetails.User.
                    withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();

            if (!jwtService.isValidToken(TYPE, refreshToken, userDetails)) {
                throw new RuntimeException("Invalid refresh token");
            }

            String newAccessToken = jwtService.generateAccessToken(user.getEmail());
            String newRefreshToken = jwtService.generateRefreshToken(user.getEmail());

            return AuthServiceResponse.builder()
                    .token(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error decoding refresh token", e);
        }
    }
}
