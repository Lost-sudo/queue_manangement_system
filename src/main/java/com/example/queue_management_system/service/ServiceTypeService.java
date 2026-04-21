package com.example.queue_management_system.service;

import com.example.queue_management_system.dto.ServiceTypeRequest;
import com.example.queue_management_system.dto.ServiceTypeResponse;

import java.util.List;
import java.util.UUID;

public interface ServiceTypeService {
    ServiceTypeResponse createService(ServiceTypeRequest request);
    List<ServiceTypeResponse> findAll();
    ServiceTypeResponse getServiceById(UUID id);
    ServiceTypeResponse getServiceByPrefix(String prefix);
    ServiceTypeResponse getServiceByName(String name);
    ServiceTypeResponse updateServiceById(UUID id, ServiceTypeRequest request);
    void deleteServiceById(UUID id);
}
