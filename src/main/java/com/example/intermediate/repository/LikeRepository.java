package com.example.intermediate.repository;

import com.example.intermediate.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostId(Long postId);
    Optional<Like> findByCommentId(Long commentId);
    Optional<Like> findBySubCommentId(Long subCommentId);
}
