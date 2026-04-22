package com.example.queue_management_system.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CountersResponse {
    private UUID id;
    private UserResponse user;
    private ServiceTypeResponse serviceType;
}
