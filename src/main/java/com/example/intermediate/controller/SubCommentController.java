package com.example.intermediate.controller;

import com.example.intermediate.dto.request.SubCommentRequestDto;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class SubCommentController {

    private final SubCommentService subCommentService;


    @RequestMapping(value = "/api/auth/sub-comments", method = RequestMethod.POST)
    public ResponseDto<?> createSubComment(@RequestBody SubCommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return subCommentService.createSubComment(requestDto, request);
    }

    @RequestMapping(value = "/api/sub-comments/{commentId}", method = RequestMethod.GET)
    public ResponseDto<?> getAllSubComments(@PathVariable Long commentId) {
        return subCommentService.getAllSubCommentsByComment(commentId);
    }

    @RequestMapping(value = "/api/auth/sub-comments/{subCommentId}", method = RequestMethod.PUT)
    public ResponseDto<?> updateSubComment(@PathVariable Long subCommentId, @RequestBody SubCommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return subCommentService.updateSubComment(subCommentId, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/sub-comments/{subCommentId}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteSubComment(@PathVariable Long subCommentId,
                                        HttpServletRequest request) {
        return subCommentService.deleteSubComment(subCommentId, request);
    }
}
