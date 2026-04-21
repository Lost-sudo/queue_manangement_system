package com.example.queue_management_system.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ServiceTypeResponse {
    private UUID id;
    private String name;
    private String prefix;
    private String description;
}
