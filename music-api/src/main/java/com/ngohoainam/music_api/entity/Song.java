package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "songs",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title","album_id", "artist_id"})

)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Song {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "title", nullable = false)
    String title;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    Album album;

    @Column(name = "duration_seconds")
    int durationSeconds;

    @Column(name = "explicit")
    boolean explicit;

    @Column(name = "is_published", nullable = false)
    boolean isPublished;

    @Column(name = "price_cents", nullable = false)
    int priceCents;

    @Column(name = "sku")
    String sku;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

}
