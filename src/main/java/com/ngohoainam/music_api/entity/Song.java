package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "description")
    String description;

    @Column(name = "explicit")
    boolean explicit = false;

    @Column(name = "is_published", nullable = false)
    boolean isPublished ;

    @Column(name = "price_cents", nullable = false)
    Integer priceCents ;

    @Column(name = "sku")
    String sku;



    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "song",cascade = CascadeType.ALL)
    private List<SongFile> songFiles;
}
