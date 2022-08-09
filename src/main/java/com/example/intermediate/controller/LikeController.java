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

    @PostMapping("/post/like/{postId}")
    public ResponseDto<?> likeByPostId(@PathVariable Long postId, HttpServletRequest request) {
        return likeService.likeByPostid(postId, request);
    }

    @DeleteMapping("/post/like/{postId}")
    public ResponseDto<?> unLikeByPostId(@PathVariable Long postId, HttpServletRequest request) {
        return likeService.unLikeByPostId(postId, request);
    }

    @PostMapping("/comment/like/{commentId}")
    public ResponseDto<?> likeByCommentId(@PathVariable Long commentId, HttpServletRequest request) {
        return likeService.likeByCommentId(commentId, request);
    }

    @DeleteMapping("/comment/like/{commentId}")
    public ResponseDto<?> unLikeByCommentId(@PathVariable Long commentId, HttpServletRequest request) {
        return likeService.unLikeByCommentId(commentId, request);
    }

}
