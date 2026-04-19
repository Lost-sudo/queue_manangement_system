package com.example.queue_management_system.dto;

import com.example.queue_management_system.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private Role role;
}
