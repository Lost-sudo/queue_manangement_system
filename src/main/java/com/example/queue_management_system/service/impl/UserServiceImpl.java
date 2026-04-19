package com.example.queue_management_system.service.impl;

import com.example.queue_management_system.domain.Role;
import com.example.queue_management_system.domain.User;
import com.example.queue_management_system.dto.AuthResponse;
import com.example.queue_management_system.dto.UserLoginRequest;
import com.example.queue_management_system.dto.UserRegisterRequest;
import com.example.queue_management_system.dto.UserResponse;
import com.example.queue_management_system.mapper.UserMapper;
import com.example.queue_management_system.repository.UserRepository;
import com.example.queue_management_system.security.JwtService;
import com.example.queue_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public AuthResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        String accessToken = jwtService.generateAccessToken(user.getEmail());

        return AuthResponse.builder()
                .token(accessToken)
                .build();
    }
}
