package com.ngohoainam.music_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class PlaylistItemId implements Serializable {
    private static final long serialVersionUID = 9179621200139117278L;
    @NotNull
    @Column(name = "playlist_id", nullable = false)
    private Long playlistId;

    @Column(name = "position", columnDefinition = "int UNSIGNED not null")
    private Long position;


}

