package com.example.queue_management_system.controller;

import com.example.queue_management_system.domain.Counter;
import com.example.queue_management_system.service.CountersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/counters")
@RequiredArgsConstructor
public class CountersController {
    private final CountersService countersService;

    @GetMapping
    public List<Counter> getCounters() {
        return countersService.getCounters();
    }

    @GetMapping("/{id}")
    public Counter getCounter(@PathVariable UUID id) {
        return countersService.getCounter(id);
    }

    @GetMapping("/{service_id}")
    public List<Counter> getCountersByServiceId(@PathVariable UUID service_id) {
        return countersService.getCounterByServiceId(service_id);
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
