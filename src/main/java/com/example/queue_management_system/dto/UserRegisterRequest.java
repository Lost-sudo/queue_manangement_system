package com.example.queue_management_system.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
