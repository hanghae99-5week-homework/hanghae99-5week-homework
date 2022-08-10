package com.example.intermediate.service;

import com.example.intermediate.domain.*;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final SubCommentService subCommentService;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> likeByPostid(Long postId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (likeRepository.findByMemberAndPost(member, post) == null) {
            Like like = Like.builder()
                    .post(post)
                    .member(member)
                    .build();
            likeRepository.save(like);
            return ResponseDto.success("Like success");
        } else {
            return ResponseDto.fail("ALREADY_EXIST", "이미 좋아요를 누르셨습니다.");
        }
    }

    @Transactional
    public ResponseDto<?> unLikeByPostId(Long postId, HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Like like = isPresentLikeByPostId(postId);
        if (null == like) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 좋아요 id 입니다.");
        }

        if (like.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 취소할 수 있습니다.");
        }

        Like deleteLike = likeRepository.findByMemberAndPost(member, post);
        likeRepository.delete(deleteLike);

        return ResponseDto.success("Unlike success");
    }

    @Transactional
    public ResponseDto<?> likeByCommentId(Long commentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (likeRepository.findByMemberAndComment(member, comment) == null) {
            Like like = Like.builder()
                    .comment(comment)
                    .member(member)
                    .build();
            likeRepository.save(like);
            return ResponseDto.success("Like success");
        } else {
            return ResponseDto.fail("ALREADY_EXIST", "이미 좋아요를 누르셨습니다.");
        }
    }

    @Transactional
    public ResponseDto<?> unLikeByCommentId(Long commentId, HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Like like = isPresentLikeByCommentId(commentId);
        if (null == like) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 좋아요 id 입니다.");
        }

        if (like.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 취소할 수 있습니다.");
        }

        Like deleteLike = likeRepository.findByMemberAndComment(member, comment);
        likeRepository.delete(deleteLike);

        return ResponseDto.success("Unlike success");
    }

    @Transactional
    public ResponseDto<?> likeBySubCommentId(Long SubCommentId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        SubComment subComment = subCommentService.isPresentSubComment(SubCommentId);
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        if (likeRepository.findByMemberAndSubComment(member, subComment) == null) {
            Like like = Like.builder()
                    .subComment(subComment)
                    .member(member)
                    .build();
            likeRepository.save(like);
            return ResponseDto.success("Like success");
        } else {
            return ResponseDto.fail("ALREADY_EXIST", "이미 좋아요를 누르셨습니다.");
        }
    }

    @Transactional
    public ResponseDto<?> unLikeBySubCommentId(Long SubCommentId, HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        SubComment subComment = subCommentService.isPresentSubComment(SubCommentId);
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        Like like = isPresentLikeBySubCommentId(SubCommentId);
        if (null == like) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 좋아요 id 입니다.");
        }

        if (like.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 취소할 수 있습니다.");
        }

        Like deleteLike = likeRepository.findByMemberAndSubComment(member, subComment);
        if (deleteLike == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 좋아요 입니다.");
        } else {
            likeRepository.delete(deleteLike);
            return ResponseDto.success("Unlike success");
        }
    }

    @Transactional(readOnly = true)
    public Like isPresentLikeByPostId(Long postId) {
        Optional<Like> optionalLike = likeRepository.findByPostId(postId);
        return optionalLike.orElse(null);
    }

    @Transactional(readOnly = true)
    public Like isPresentLikeByCommentId(Long commentId) {
        Optional<Like> optionalLike = likeRepository.findByCommentId(commentId);
        return optionalLike.orElse(null);
    }

    @Transactional(readOnly = true)
    public Like isPresentLikeBySubCommentId(Long subCommentId) {
        Optional<Like> optionalLike = likeRepository.findBySubCommentId(subCommentId);
        return optionalLike.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
