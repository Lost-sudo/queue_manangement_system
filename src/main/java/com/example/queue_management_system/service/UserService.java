package com.example.queue_management_system.service;

import com.example.queue_management_system.dto.*;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    UserResponse register(UserRegisterRequest request);
    AuthServiceResponse login(UserLoginRequest request);
    AuthServiceResponse refreshToken(HttpServletRequest request);
}
