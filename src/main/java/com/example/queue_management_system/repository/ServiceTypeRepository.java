package com.example.queue_management_system.repository;

import com.example.queue_management_system.domain.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, UUID> {
    @Query("SELECT s FROM ServiceType s WHERE s.prefix = :prefix")
    Optional<ServiceType> findByPrefix(String prefix);

    @Query("SELECT s FROM ServiceType s WHERE s.name = :serviceName")
    Optional<ServiceType> findByServiceName(String serviceName);
}
