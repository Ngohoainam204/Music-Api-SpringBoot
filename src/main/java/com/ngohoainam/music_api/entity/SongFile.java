package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "song_files")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @Size(max = 255)
    @NotNull
    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Size(max = 255)
    @Column(name = "storage_provider")
    private String storageProvider;

    @Column(name = "bitrate_kbps")
    private Integer bitrateKbps;

    @Size(max = 255)
    @Column(name = "format")
    private String format;

    @Column(name = "filesize_bytes")
    private Long filesizeBytes;

    @Column(name = "uploaded_at")
    private Instant uploadedAt;


}

