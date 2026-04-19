package com.example.queue_management_system.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String email;
    private String password;
    private String confirmPassword;
}
