package com.example.intermediate.controller;

import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like/post/{postId}")
    public ResponseDto<?> likeByPostId(@PathVariable Long postId, HttpServletRequest request) {
        return likeService.likeByPostid(postId, request);
    }

    @DeleteMapping("/like/post/{postId}")
    public ResponseDto<?> unLikeByPostId(@PathVariable Long postId, HttpServletRequest request) {
        return likeService.unLikeByPostId(postId, request);
    }

    @PostMapping("/like/comment/{commentId}")
    public ResponseDto<?> likeByCommentId(@PathVariable Long commentId, HttpServletRequest request) {
        return likeService.likeByCommentId(commentId, request);
    }

    @DeleteMapping("/like/comment/{commentId}")
    public ResponseDto<?> unLikeByCommentId(@PathVariable Long commentId, HttpServletRequest request) {
        return likeService.unLikeByCommentId(commentId, request);
    }

    @PostMapping("/like/sub-comment/{subCommentId}")
    public ResponseDto<?> likeBySubCommentId(@PathVariable Long subCommentId, HttpServletRequest request) {
        return likeService.likeBySubCommentId(subCommentId, request);
    }

    @DeleteMapping("/like/sub-comment/{subCommentId}")
    public ResponseDto<?> unLikeBySubCommentId(@PathVariable Long subCommentId, HttpServletRequest request) {
        return likeService.unLikeBySubCommentId(subCommentId, request);
    }

}
