package com.example.intermediate.repository;

import com.example.intermediate.domain.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
  List<Comment> findAllByMemberId(Long memberId);

  Long countByPostId(Long postId);

}
