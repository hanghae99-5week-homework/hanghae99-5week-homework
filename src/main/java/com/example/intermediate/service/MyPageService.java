package com.example.intermediate.service;

import com.example.intermediate.domain.*;
import com.example.intermediate.dto.response.*;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final SubCommentRepository subCommentRepository;
    private final LikeRepository likeRepository;

    private final TokenProvider tokenProvider;

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllContentsByMember(HttpServletRequest request) {
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

        List<Post> postList = postRepository.findAllByMemberId(member.getId());
        if (null == postList) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(
                PostResponseDto.builder()
                    .id(post.getId())
                    .author(post.getMember().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imgUrl(post.getImgUrl())
                    .likesCount(likeRepository.countByPostId(post.getId()))
                    .commentsCount(commentRepository.countByPostId(post.getId()))
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build()
            );
        }

        List<Comment> commentList = commentRepository.findAllByMemberId(member.getId());
        if (null == commentList) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .author(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .likesCount(likeRepository.countByCommentId(comment.getId()))
                            .subCommentsCount(subCommentRepository.countByCommentId(comment.getId()))
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        List<SubComment> subCommentList = subCommentRepository.findAllByMemberId(member.getId());
        if (null == subCommentList) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 대댓글 id 입니다.");
        }

        List<SubCommentResponseDto> subCommentResponseDtoList = new ArrayList<>();
        for (SubComment subComment : subCommentList) {
            subCommentResponseDtoList.add(
                    SubCommentResponseDto.builder()
                            .id(subComment.getId())
                            .author(subComment.getMember().getNickname())
                            .content(subComment.getContent())
                            .likesCount(likeRepository.countBySubCommentId(subComment.getId()))
                            .createdAt(subComment.getCreatedAt())
                            .modifiedAt(subComment.getModifiedAt())
                            .build()
            );
        }

        List<Like> likeByMember = likeRepository.findByMember(member);
        List<Post> likePostList = new ArrayList<>();
        List<Comment> likeCommentList = new ArrayList<>();
        List<SubComment> likeSubCommentList = new ArrayList<>();

        for (Like like : likeByMember) {
            if (like.getPost() != null) {
                likePostList.add(like.getPost());
            } else if (like.getComment() != null) {
                likeCommentList.add(like.getComment());
            } else if (like.getSubComment() != null) {
                likeSubCommentList.add(like.getSubComment());
            }
        }

        List<PostResponseDto> likePostResponseDtoList = new ArrayList<>();
        for (Post post : likePostList) {
            likePostResponseDtoList.add(
                PostResponseDto.builder()
                    .id(post.getId())
                    .author(post.getMember().getNickname())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imgUrl(post.getImgUrl())
                    .likesCount(likeRepository.countByPostId(post.getId()))
                    .commentsCount(commentRepository.countByPostId(post.getId()))
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build()
            );
        }

        List<CommentResponseDto> likeCommentResponseDtoList = new ArrayList<>();
        for (Comment comment : likeCommentList) {
            likeCommentResponseDtoList.add(
                CommentResponseDto.builder()
                    .id(comment.getId())
                    .author(comment.getMember().getNickname())
                    .content(comment.getContent())
                    .likesCount(likeRepository.countByCommentId(comment.getId()))
                    .subCommentsCount(subCommentRepository.countByCommentId(comment.getId()))
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build()
            );
        }

        List<SubCommentResponseDto> likeSubCommentResponseDtoList = new ArrayList<>();
        for (SubComment subComment : likeSubCommentList) {
            likeSubCommentResponseDtoList.add(
                SubCommentResponseDto.builder()
                    .id(subComment.getId())
                    .author(subComment.getMember().getNickname())
                    .content(subComment.getContent())
                    .likesCount(likeRepository.countBySubCommentId(subComment.getId()))
                    .createdAt(subComment.getCreatedAt())
                    .modifiedAt(subComment.getModifiedAt())
                    .build()
            );
        }

        LikeResponseDto likeResponseDto = LikeResponseDto.builder()
            .likePostResponseDtoList(likePostResponseDtoList)
            .likeCommentResponseDtoList(likeCommentResponseDtoList)
            .likeSubCommentResponseDtoList(likeSubCommentResponseDtoList)
            .build();

        return ResponseDto.success(
            MyPageResponseDto.builder()
                .postResponseDtoList(postResponseDtoList)
                .commentResponseDtoList(commentResponseDtoList)
                .subCommentResponseDtoList(subCommentResponseDtoList)
                .likeResponseDto(likeResponseDto)
                .build()
        );
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
