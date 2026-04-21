package com.example.queue_management_system.service;

import com.example.queue_management_system.domain.Counter;
import com.example.queue_management_system.dto.CountersRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountersService {
    Counter createCounter(CountersRequest request);
    List<Counter> getCounters();
    Counter getCounter(UUID id);
    List<Counter> getCounterByServiceId(UUID service_id);
    void setCounterActiveStatus(UUID id, boolean isActive);
    void setCounterInactiveStatus(UUID id, boolean isInactive);
    void deleteCounter(UUID id);
}
