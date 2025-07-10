package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)

    private String url;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
    private int duration;
    private String lyric;
    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

}
