package com.example.queue_management_system.mapper;

import com.example.queue_management_system.domain.ServiceType;
import com.example.queue_management_system.dto.ServiceTypeResponse;

public class ServiceTypeMapper {
    public static ServiceTypeResponse toServiceTypeResponse(ServiceType serviceType) {
        return ServiceTypeResponse.builder()
                .id(serviceType.getId())
                .name(serviceType.getName())
                .prefix(serviceType.getPrefix())
                .description(serviceType.getDescription())
                .build();
    }
}
