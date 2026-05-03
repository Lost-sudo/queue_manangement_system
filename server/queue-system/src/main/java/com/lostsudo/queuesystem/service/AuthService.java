package com.lostsudo.queuesystem.service;

import com.lostsudo.queuesystem.dto.request.LoginRequest;
import com.lostsudo.queuesystem.dto.request.RefreshTokenRequest;
import com.lostsudo.queuesystem.dto.request.RegisterRequest;
import com.lostsudo.queuesystem.dto.response.AuthResponse;
import com.lostsudo.queuesystem.dto.response.MessageResponse;
import com.lostsudo.queuesystem.entity.RefreshToken;
import com.lostsudo.queuesystem.entity.User;
import com.lostsudo.queuesystem.exception.BadRequestException;
import com.lostsudo.queuesystem.exception.ConflictException;
import com.lostsudo.queuesystem.repository.RefreshTokenRepository;
import com.lostsudo.queuesystem.repository.UserRepository;
import com.lostsudo.queuesystem.security.jwt.JwtUtils;
import com.lostsudo.queuesystem.security.services.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MessageResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .schoolId(request.getSchoolId())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(request.getRoles() != null ? request.getRoles() : Set.of("ROLE_USER"))
                .build();

        userRepository.save(user);
        return MessageResponse.builder().message("User registered successfully!").build();
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        assert userDetails != null;
        String accessToken = jwtUtils.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(requestRefreshToken);

        refreshTokenService.revokeToken(refreshToken);

        User user = refreshToken.getUser();
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        String newAccessToken = jwtUtils.generateAccessToken(userDetails);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();
    }

    public MessageResponse logout(RefreshTokenRequest request) {
        String tokenValue = request.getRefreshToken();
        refreshTokenRepository.findByToken(tokenValue)
                .ifPresent(refreshTokenService::revokeToken);
        return MessageResponse.builder()
                .message("Successfully logged out!")
                .build();
    }
}
