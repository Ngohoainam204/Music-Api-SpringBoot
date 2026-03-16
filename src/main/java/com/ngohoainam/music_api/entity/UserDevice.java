package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "user_devices",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_device", columnNames = {"user_id", "device_id"})
        }
)
public class UserDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            columnDefinition = "BIGINT UNSIGNED"
    )
    private User user;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "device_name")
    private String deviceName; // Optional: to store user-friendly device name

    @Column(name = "last_login", nullable = false)
    private Instant lastLogin;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}


