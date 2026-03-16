package com.ngohoainam.music_api.service;

import com.ngohoainam.music_api.dto.request.commentRequest.CommentCreateRequest;
import com.ngohoainam.music_api.dto.request.commentRequest.CommentUpdateRequest;
import com.ngohoainam.music_api.dto.response.CommentResponse;
import com.ngohoainam.music_api.entity.Comment;
import com.ngohoainam.music_api.entity.Song;
import com.ngohoainam.music_api.entity.User;
import com.ngohoainam.music_api.enums.Roles;
import com.ngohoainam.music_api.exception.AppException;
import com.ngohoainam.music_api.exception.ErrorCode;
import com.ngohoainam.music_api.repository.CommentRepository;
import com.ngohoainam.music_api.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final SongRepository songRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public CommentResponse createComment(Long songId, CommentCreateRequest request) {
        User currentUser = authenticationService.getCurrentUser();
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new AppException(ErrorCode.SONG_NOT_FOUND));

        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
            if (!parentComment.getSong().getId().equals(songId)) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }
        }

        validatePlaybackSecond(song, request.getPlaybackSecond());

        Comment comment = new Comment();
        comment.setSong(song);
        comment.setUser(currentUser);
        comment.setParentComment(parentComment);
        comment.setContent(request.getContent().trim());
        comment.setPlaybackSecond(request.getPlaybackSecond());

        return toResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> getRootCommentsBySong(Long songId) {
        songRepository.findById(songId).orElseThrow(() -> new AppException(ErrorCode.SONG_NOT_FOUND));
        return commentRepository.findBySongIdAndParentCommentIsNullOrderByCreatedAtDesc(songId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<CommentResponse> getReplies(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
        return commentRepository.findByParentCommentIdOrderByCreatedAtAsc(commentId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        User currentUser = authenticationService.getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        ensureCommentOwnerOrAdmin(currentUser, comment);
        comment.setContent(request.getContent().trim());

        return toResponse(commentRepository.save(comment));
    }

    @Transactional
    public void deleteComment(Long commentId) {
        User currentUser = authenticationService.getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        ensureCommentOwnerOrAdmin(currentUser, comment);
        commentRepository.delete(comment);
    }

    private void ensureCommentOwnerOrAdmin(User currentUser, Comment comment) {
        boolean isOwner = comment.getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRoles() == Roles.ADMIN;
        if (!isOwner && !isAdmin) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
    }

    private void validatePlaybackSecond(Song song, Long playbackSecond) {
        if (playbackSecond == null) return;
        if (playbackSecond < 0) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        Long duration = song.getDurationSeconds();
        if (duration != null && playbackSecond > duration) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
    }

    private CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .songId(comment.getSong().getId())
                .userId(comment.getUser().getId())
                .userDisplayName(comment.getUser().getDisplayName())
                .parentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .content(comment.getContent())
                .playbackSecond(comment.getPlaybackSecond())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .replyCount(commentRepository.countByParentCommentId(comment.getId()))
                .build();
    }
}
