package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "albums")
@Entity
public class Album {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(name = "release_date", nullable = false)
    private LocalDateTime releaseDate;

    @Column(name = "cover_image", nullable = false)
    private String coverImage;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "album",cascade = CascadeType.ALL)
    private Set<Song> songs = new java.util.HashSet<>();
}
