package com.example.queue_management_system.repository;

import com.example.queue_management_system.domain.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountersRepository extends JpaRepository<Counter, UUID> {
    Optional<Counter> findByName(String name);
    Optional<Counter> findByNameAndIsActiveTrue(String name);
    List<Counter> findByServiceIdAndIsActiveTrue(UUID serviceId);
}
