package com.example.intermediate.repository;

import com.example.intermediate.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostId(Long postId);
    Optional<Like> findByCommentId(Long commentId);
    Optional<Like> findBySubCommentId(Long subCommentId);

    Like findByMemberAndPost(Member member, Post post);
    Like findByMemberAndComment(Member member, Comment comment);
    Like findByMemberAndSubComment(Member member, SubComment subComment);

    Long countByPostId(Long postId);
    Long countByCommentId(Long commentId);
    Long countBySubCommentId(Long subCommentId);
}
