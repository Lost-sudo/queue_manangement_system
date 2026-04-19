package com.example.queue_management_system.controller;

import com.example.queue_management_system.dto.AuthResponse;
import com.example.queue_management_system.dto.UserLoginRequest;
import com.example.queue_management_system.dto.UserRegisterRequest;
import com.example.queue_management_system.dto.UserResponse;
import com.example.queue_management_system.service.UserService;
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

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}
