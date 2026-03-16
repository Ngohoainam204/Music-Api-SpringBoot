package com.ngohoainam.music_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "plays")
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "played_at")
    private Instant playedAt;

    @ColumnDefault("'0'")
    @Column(name = "duration_seconds", columnDefinition = "int UNSIGNED")
    private Long durationSeconds;

    @Size(max = 45)
    @Column(name = "ip", length = 45)
    private String ip;

    @Size(max = 512)
    @Column(name = "user_agent", length = 512)
    private String userAgent;

    @Size(max = 255)
    @Column(name = "device_info")
    private String deviceInfo;


}

