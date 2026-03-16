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
public class FollowId implements Serializable {
    private static final long serialVersionUID = 7613096844385707292L;
    
    @NotNull
    @Column(name = "follower_id", nullable = false)
    private Long followerId;

    @NotNull
    @Column(name = "followee_id", nullable = false)
    private Long followeeId;

    @NotNull
    @Column(name = "followee_type", nullable = false, length = 50)
    private String followeeType;

}

