package com.example.queue_management_system.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CountersRequest {
    private String name;
    private UUID service_id;
    private UUID assigned_staff_id;
}
