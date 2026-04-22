package com.example.queue_management_system.repository;

import com.example.queue_management_system.domain.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountersRepository extends JpaRepository<Counter, UUID> {
    @Query("SELECT c FROM Counter c WHERE c.service.id = :serviceId AND c.isActive = true")
    List<Counter> findByServiceIdAndIsActiveTrue(UUID serviceId);
}
