package com.example.queue_management_system.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthServiceResponse {
    private String token;
    private String refreshToken;
}
