package com.ngohoainam.music_api.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    Long id;
    Long songId;
    Long userId;
    String userDisplayName;
    Long parentCommentId;
    String content;
    Long playbackSecond;
    Instant createdAt;
    Instant updatedAt;
    Long replyCount;
}
