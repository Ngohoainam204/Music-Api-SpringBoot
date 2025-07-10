package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "artist")
@Entity
public class Artist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    private String url;

    private String bio;
    @OneToMany(mappedBy = "artist",cascade = CascadeType.ALL)
    private Set<Song> songs = new HashSet<>();

    @OneToMany(mappedBy = "artist",cascade = CascadeType.ALL)
    private Set<Album> albums = new HashSet<>();
}
