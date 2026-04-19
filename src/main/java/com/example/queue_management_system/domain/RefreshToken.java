package com.example.queue_management_system.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean isRevoked;

    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;

    @PrePersist
    public void prePersist() {
        this.issuedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.expiredAt = LocalDateTime.now();
    }
}
