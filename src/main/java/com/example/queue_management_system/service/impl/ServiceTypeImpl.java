package com.example.queue_management_system.service.impl;

import com.example.queue_management_system.domain.ServiceType;
import com.example.queue_management_system.dto.ServiceTypeRequest;
import com.example.queue_management_system.dto.ServiceTypeResponse;
import com.example.queue_management_system.mapper.ServiceTypeMapper;
import com.example.queue_management_system.repository.ServiceTypeRepository;
import com.example.queue_management_system.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ServiceTypeImpl implements ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;

    @Override
    public ServiceTypeResponse createService(ServiceTypeRequest request) {
        if (serviceTypeRepository.findByServiceName(request.getName()).isPresent()) {
            throw new RuntimeException("Service already exists");
        }

        if (serviceTypeRepository.findByPrefix(request.getPrefix()).isPresent()) {
            throw new RuntimeException("Prefix already exists");
        }

        ServiceType serviceType = ServiceType.builder()
                .name(request.getName())
                .prefix(request.getPrefix())
                .description(request.getDescription())
                .build();

        ServiceType savedServiceType = serviceTypeRepository.save(serviceType);

        return ServiceTypeMapper.toServiceTypeResponse(savedServiceType);
    }

    @Override
    public List<ServiceTypeResponse> findAll() {
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAll();

        return serviceTypeList.stream()
                .map(ServiceTypeMapper::toServiceTypeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ServiceTypeResponse getServiceById(UUID id) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        return ServiceTypeMapper.toServiceTypeResponse(serviceType);
    }

    @Override
    public ServiceTypeResponse getServiceByPrefix(String prefix) {
        ServiceType serviceType = serviceTypeRepository.findByPrefix(prefix)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        return ServiceTypeMapper.toServiceTypeResponse(serviceType);
    }

    @Override
    public ServiceTypeResponse getServiceByName(String name) {
        ServiceType serviceType = serviceTypeRepository.findByServiceName(name)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        return ServiceTypeMapper.toServiceTypeResponse(serviceType);
    }

    @Override
    public ServiceTypeResponse updateServiceById(UUID id, ServiceTypeRequest request) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (serviceTypeRepository.findByServiceName(request.getName()).isPresent()) {
            throw new RuntimeException("Service already exists");
        }

        if (serviceTypeRepository.findByPrefix(request.getPrefix()).isPresent()) {
            throw new RuntimeException("Prefix already exists");
        }

        serviceType.setName(request.getName());
        serviceType.setPrefix(request.getPrefix());
        serviceType.setDescription(request.getDescription());

        ServiceType updatedServiceType = serviceTypeRepository.save(serviceType);

        return ServiceTypeMapper.toServiceTypeResponse(updatedServiceType);
    }

    @Override
    public void deleteServiceById(UUID id) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        serviceTypeRepository.delete(serviceType);
    }
}
