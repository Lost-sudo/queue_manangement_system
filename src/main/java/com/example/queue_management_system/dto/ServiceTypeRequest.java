package com.example.queue_management_system.dto;

import lombok.Data;

@Data
public class ServiceTypeRequest {
    private String name;
    private String prefix;
    private String description;
}
