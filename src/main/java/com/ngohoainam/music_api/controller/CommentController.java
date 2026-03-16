package com.ngohoainam.music_api.controller;

import com.ngohoainam.music_api.dto.ApiResponse;
import com.ngohoainam.music_api.dto.request.commentRequest.CommentCreateRequest;
import com.ngohoainam.music_api.dto.request.commentRequest.CommentUpdateRequest;
import com.ngohoainam.music_api.dto.response.CommentResponse;
import com.ngohoainam.music_api.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/songs/{songId}/comments")
    @PreAuthorize("hasAnyRole('USER', 'ARTIST', 'ADMIN')")
    public ApiResponse<CommentResponse> createComment(
            @PathVariable("songId") Long songId,
            @RequestBody @Valid CommentCreateRequest request
    ) {
        return ApiResponse.created(commentService.createComment(songId, request));
    }

    @GetMapping("/songs/{songId}/comments")
    public ApiResponse<List<CommentResponse>> getCommentsBySong(@PathVariable("songId") Long songId) {
        return ApiResponse.success(commentService.getRootCommentsBySong(songId));
    }

    @GetMapping("/comments/{commentId}/replies")
    public ApiResponse<List<CommentResponse>> getReplies(@PathVariable("commentId") Long commentId) {
        return ApiResponse.success(commentService.getReplies(commentId));
    }

    @PutMapping("/comments/{commentId}")
    @PreAuthorize("hasAnyRole('USER', 'ARTIST', 'ADMIN')")
    public ApiResponse<CommentResponse> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody @Valid CommentUpdateRequest request
    ) {
        return ApiResponse.success(commentService.updateComment(commentId, request));
    }

    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("hasAnyRole('USER', 'ARTIST', 'ADMIN')")
    public ApiResponse<String> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.success("Comment deleted successfully");
    }
}
