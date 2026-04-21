package com.example.queue_management_system.controller;

import com.example.queue_management_system.dto.ServiceTypeRequest;
import com.example.queue_management_system.dto.ServiceTypeResponse;
import com.example.queue_management_system.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;

    @GetMapping("/")
    public List<ServiceTypeResponse> getAllServiceType() {
        return serviceTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ServiceTypeResponse getServiceById(@PathVariable UUID id) {
        System.out.println(id);
        return serviceTypeService.getServiceById(id);
    }

    @GetMapping("/prefix/{prefix}")
    public ServiceTypeResponse getServiceByPrefix(@PathVariable String prefix) {
        return serviceTypeService.getServiceByPrefix(prefix);
    }

    @GetMapping("/name/{name}")
    public ServiceTypeResponse getServiceByName(@PathVariable String name) {
        return serviceTypeService.getServiceByName(name);
    }

    @PostMapping("/create-service")
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceTypeResponse addServiceType(@RequestBody ServiceTypeRequest request) {
        return serviceTypeService.createService(request);
    }

    @PutMapping("/update-service/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ServiceTypeResponse updateServiceById(@PathVariable UUID id, @RequestBody ServiceTypeRequest request) {
        return serviceTypeService.updateServiceById(id, request);
    }

    @DeleteMapping("/delete-service/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteServiceById(@PathVariable UUID id) {
        serviceTypeService.deleteServiceById(id);
    }
}
