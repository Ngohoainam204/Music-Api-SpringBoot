package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "artists")
@Entity
public class Artist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    String url;

    @Column(name = "bio")
    String bio;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    LocalDateTime createdAt;
    @OneToMany(mappedBy = "artist",cascade = CascadeType.ALL)
    Set<Song> songs = new HashSet<>();

    @OneToMany(mappedBy = "artist",cascade = CascadeType.ALL)
     Set<Album> albums = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",unique = true)
    User user;
}
