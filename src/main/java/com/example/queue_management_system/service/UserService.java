package com.example.queue_management_system.service;

import com.example.queue_management_system.dto.AuthResponse;
import com.example.queue_management_system.dto.UserLoginRequest;
import com.example.queue_management_system.dto.UserRegisterRequest;
import com.example.queue_management_system.dto.UserResponse;

public interface UserService {
    public UserResponse register(UserRegisterRequest request);
    public AuthResponse login(UserLoginRequest request);

}
