package com.example.queue_management_system.controller;

import com.example.queue_management_system.domain.Counter;
import com.example.queue_management_system.dto.CountersRequest;
import com.example.queue_management_system.dto.CountersResponse;
import com.example.queue_management_system.service.CountersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/counters")
@RequiredArgsConstructor
public class CountersController {
    private final CountersService countersService;

    @GetMapping("/")
    public List<CountersResponse> getCounters() {
        return countersService.getCounters();
    }

    @GetMapping("/{id}")
    public CountersResponse getCounter(@PathVariable UUID id) {
        return countersService.getCounter(id);
    }

    @GetMapping("/by-service/{service_id}")
    public List<CountersResponse> getCountersByServiceId(@PathVariable UUID service_id) {
        return countersService.getAllCountersByServiceId(service_id);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public CountersResponse createCounter(@RequestBody CountersRequest request) {
        return countersService.createCounter(request);
    }

    @PutMapping("/{id}/activate")
    public void setCounterActiveStatus(@PathVariable UUID id) {
        countersService.setCounterActiveStatus(id, true);
    }

    @PutMapping("{id}/deactivate")
    public void setCounterDeactivatedStatus(@PathVariable UUID id) {
        countersService.setCounterActiveStatus(id, false);
    }

    @DeleteMapping("{id}")
    public void deleteCounter(@PathVariable UUID id) {
        countersService.deleteCounter(id);
    }
}
