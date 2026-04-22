package com.example.queue_management_system.service.impl;

import com.example.queue_management_system.domain.Counter;
import com.example.queue_management_system.domain.ServiceType;
import com.example.queue_management_system.domain.User;
import com.example.queue_management_system.dto.CountersRequest;
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
    public Counter createCounter(CountersRequest request) {
        ServiceType serviceType = serviceTypeRepository.findById(request.getService_id())
                .orElseThrow(() -> new RuntimeException("Service with id " + request.getService_id() + " not found"));

        User user = userRepository.findById(request.getAssigned_staff_id())
                .orElseThrow(() -> new RuntimeException("User with id " + request.getAssigned_staff_id() + " not found"));

        Counter counter = Counter.builder()
                .name(request.getName())
                .service(serviceType)
                .assignedStaff(user)
                .build();

        return countersRepository.save(counter);
    }

    @Override
    public List<Counter> getCounters() {
        return countersRepository.findAll();
    }

    @Override
    public Counter getCounter(UUID id) {
        return countersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Counter with id " + id + " not found"));
    }

    @Override
    public List<Counter> getCounterByServiceId(UUID service_id) {
        List<Counter> counters = countersRepository.findByServiceIdAndIsActiveTrue(service_id);

        return counters.stream()
                .peek(counter -> counter.setIsActive(counter.getIsActive())).collect(Collectors.toList());

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
