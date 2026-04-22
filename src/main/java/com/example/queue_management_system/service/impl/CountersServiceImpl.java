package com.example.queue_management_system.service.impl;

import com.example.queue_management_system.domain.Counter;
import com.example.queue_management_system.domain.ServiceType;
import com.example.queue_management_system.domain.User;
import com.example.queue_management_system.dto.CountersRequest;
import com.example.queue_management_system.dto.CountersResponse;
import com.example.queue_management_system.mapper.ServiceTypeMapper;
import com.example.queue_management_system.mapper.UserMapper;
import com.example.queue_management_system.repository.CountersRepository;
import com.example.queue_management_system.repository.ServiceTypeRepository;
import com.example.queue_management_system.repository.UserRepository;
import com.example.queue_management_system.service.CountersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CountersServiceImpl implements CountersService {

    private final CountersRepository countersRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final UserRepository userRepository;

    @Override
    public CountersResponse createCounter(CountersRequest request) {
        ServiceType serviceType = serviceTypeRepository.findById(request.getService_id())
                .orElseThrow(() -> new RuntimeException("Service with id " + request.getService_id() + " not found"));

        User user = userRepository.findById(request.getAssigned_staff_id())
                .orElseThrow(() -> new RuntimeException("User with id " + request.getAssigned_staff_id() + " not found"));

        Counter counter = Counter.builder()
                .name(request.getName())
                .service(serviceType)
                .assignedStaff(user)
                .build();

        Counter savedCounter = countersRepository.save(counter);

        return CountersResponse.builder()
                .id(savedCounter.getId())
                .user(UserMapper.toUserResponse(user))
                .serviceType(ServiceTypeMapper.toServiceTypeResponse(serviceType))
                .build();
    }

    @Override
    public List<CountersResponse> getCounters() {
        List<Counter> counters = countersRepository.findAll();

        return counters.stream()
                .map(counter -> CountersResponse.builder()
                        .id(counter.getId())
                        .user(UserMapper.toUserResponse(counter.getAssignedStaff()))
                        .serviceType(ServiceTypeMapper.toServiceTypeResponse(counter.getService()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CountersResponse getCounter(UUID id) {
        Counter counter =  countersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Counter with id " + id + " not found"));

        return CountersResponse.builder()
                .id(counter.getId())
                .user(UserMapper.toUserResponse(counter.getAssignedStaff()))
                .serviceType(ServiceTypeMapper.toServiceTypeResponse(counter.getService()))
                .build();
    }

    @Override
    public List<CountersResponse> getAllCountersByServiceId(UUID service_id) {
        List<Counter> counters = countersRepository.findByServiceIdAndIsActiveTrue(service_id);

        return counters.stream()
                .map(counter -> CountersResponse.builder()
                        .id(counter.getId())
                        .user(UserMapper.toUserResponse(counter.getAssignedStaff()))
                        .serviceType(ServiceTypeMapper.toServiceTypeResponse(counter.getService()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void setCounterActiveStatus(UUID id, boolean isActive) {
            Counter counter = countersRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Counter with id " + id + " not found"));

            counter.setIsActive(isActive);
            countersRepository.save(counter);
    }

    @Override
    public void setCounterInactiveStatus(UUID id, boolean isInactive) {
        Counter counter = countersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Counter with id " + id + " not found"));

        counter.setIsActive(!isInactive);
        countersRepository.save(counter);
    }

    @Override
    public void deleteCounter(UUID id) {
        if (!countersRepository.existsById(id)) {
            throw new RuntimeException("Counter with id " + id + " not found");
        }
        countersRepository.deleteById(id);
    }
}
