package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "song_purchases",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_song_purchase_user_song", columnNames = {"user_id", "song_id"})
        }
)
public class SongPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Column(name = "amount_cents", precision = 10, scale = 0, nullable = false)
    private BigDecimal amountCents;

    @CreationTimestamp
    @Column(name = "purchased_at", nullable = false, updatable = false)
    private Instant purchasedAt;
}


