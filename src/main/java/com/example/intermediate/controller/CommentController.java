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

    @RequestMapping(value = "/api/comments/detail/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }

    @RequestMapping(value = "/api/comments/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllComments(@PathVariable Long id) {
        return commentService.getAllCommentsByPost(id);
    }

    @RequestMapping(value = "/api/auth/comments/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(id, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/comments/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long id,
                                        HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }
}
