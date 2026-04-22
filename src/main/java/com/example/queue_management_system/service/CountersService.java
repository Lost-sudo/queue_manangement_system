package com.example.queue_management_system.service;

import com.example.queue_management_system.domain.Counter;
import com.example.queue_management_system.dto.CountersRequest;
import com.example.queue_management_system.dto.CountersResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountersService {
    CountersResponse createCounter(CountersRequest request);
    List<CountersResponse> getCounters();
    CountersResponse getCounter(UUID id);
    List<CountersResponse> getAllCountersByServiceId(UUID service_id);
    void setCounterActiveStatus(UUID id, boolean isActive);
    void setCounterInactiveStatus(UUID id, boolean isInactive);
    void deleteCounter(UUID id);
}
