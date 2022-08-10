package com.example.intermediate.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.intermediate.dto.request.CommentRequestDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @RequestMapping(value = "/api/auth/comments", method = RequestMethod.POST)
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.createComment(requestDto, request);
    }

    @RequestMapping(value = "/api/comments/detail/{postId}", method = RequestMethod.GET)
    public ResponseDto<?> getComment(@PathVariable Long postId) {
        return commentService.getComment(postId);
    }

    @RequestMapping(value = "/api/comments/{postId}", method = RequestMethod.GET)
    public ResponseDto<?> getAllComments(@PathVariable Long postId) {
        return commentService.getAllCommentsByPost(postId);
    }

    @RequestMapping(value = "/api/auth/comments/{commentId}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(commentId, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/comments/{commentId}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long commentId,
                                        HttpServletRequest request) {
        return commentService.deleteComment(commentId, request);
    }
}
