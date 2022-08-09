package com.example.intermediate.service;

import com.example.intermediate.dto.request.SubCommentRequestDto;
import com.example.intermediate.dto.response.CommentResponseDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.dto.response.SubCommentResponseDto;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCommentService {

    private final SubCommentRepository subcommentRepository;

    private final TokenProvider tokenProvider;
    private final CommentService commentService;

    @Transactional
    public ResponseDto<?> createSubComment(SubCommentRequestDto requestDto, HttpServletRequest request) {
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

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        SubComment subcomment = SubComment.builder()
                .member(member)
                .comment(comment)
                .content(requestDto.getContent())
                .build();
        subcommentRepository.save(subcomment);
        return ResponseDto.success(
                SubCommentResponseDto.builder()
                        .id(subcomment.getId())
                        .author(subcomment.getMember().getNickname())
                        .content(subcomment.getContent())
                        .createdAt(subcomment.getCreatedAt())
                        .modifiedAt(subcomment.getModifiedAt())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllSubCommentsByComment(Long commentId) {
        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        List<SubComment> subcommentList = subcommentRepository.findAllByComment(comment);
        List<SubCommentResponseDto> subcommentResponseDtoList = new ArrayList<>();

        for (SubComment subcomment : subcommentList) {
            subcommentResponseDtoList.add(
                    SubCommentResponseDto.builder()
                            .id(subcomment.getId())
                            .author(subcomment.getMember().getNickname())
                            .content(subcomment.getContent())
                            .createdAt(subcomment.getCreatedAt())
                            .modifiedAt(subcomment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(subcommentResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> updateSubComment(Long id, SubCommentRequestDto requestDto, HttpServletRequest request) {
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

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 id 입니다.");
        }

        SubComment subcomment = isPresentSubComment(id);
        if (null == subcomment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (subcomment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        subcomment.update(requestDto);
        return ResponseDto.success(
                CommentResponseDto.builder()
                        .id(subcomment.getId())
                        .author(subcomment.getMember().getNickname())
                        .content(subcomment.getContent())
                        .createdAt(subcomment.getCreatedAt())
                        .modifiedAt(subcomment.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> deleteSubComment(Long id, HttpServletRequest request) {
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

        SubComment subcomment = isPresentSubComment(id);
        if (null == subcomment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (subcomment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }

        subcommentRepository.delete(subcomment);
        return ResponseDto.success("success");
    }

    @Transactional(readOnly = true)
    public SubComment isPresentSubComment(Long id) {
        Optional<SubComment> optionalSubComment = subcommentRepository.findById(id);
        return optionalSubComment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
