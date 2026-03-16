package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 100)
    @Column(name = "provider", length = 100)
    private String provider;

    @Size(max = 255)
    @Column(name = "provider_payment_id")
    private String providerPaymentId;

    @Column(name = "amount_cents", columnDefinition = "INT UNSIGNED not null")
    private Long amountCents;

    @Size(max = 3)
    @ColumnDefault("'VND'")
    @Column(name = "currency", length = 3)
    private String currency;

    @ColumnDefault("'initiated'")
    @Column(name = "status", length = 50)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

}

