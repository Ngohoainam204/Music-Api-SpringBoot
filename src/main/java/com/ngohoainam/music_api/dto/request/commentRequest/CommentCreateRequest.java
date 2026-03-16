package com.ngohoainam.music_api.dto.request.commentRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentCreateRequest {
    @NotBlank(message = "content must not be blank")
    String content;
    Long parentCommentId;
    @PositiveOrZero(message = "playbackSecond must be >= 0")
    Long playbackSecond;
}
