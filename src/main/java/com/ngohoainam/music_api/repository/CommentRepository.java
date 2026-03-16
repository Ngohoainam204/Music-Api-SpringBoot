package com.ngohoainam.music_api.repository;

import com.ngohoainam.music_api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findBySongIdAndParentCommentIsNullOrderByCreatedAtDesc(Long songId);
    List<Comment> findByParentCommentIdOrderByCreatedAtAsc(Long parentCommentId);
    long countByParentCommentId(Long parentCommentId);
}
