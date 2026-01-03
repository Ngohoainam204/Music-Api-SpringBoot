package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Entity @Builder
@Table(name = "song_files")
@AllArgsConstructor
public class SongFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "storage_provider")
    private String storageProvider = "s3";

    @Column(name = "bitrate_kbps")
    private Integer bitrateKbps;

    @Column(name ="format")
    private String format;

    @Column(name = "filesize_bytes")
    private Long filesizeBytes;

    @LastModifiedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;
}
