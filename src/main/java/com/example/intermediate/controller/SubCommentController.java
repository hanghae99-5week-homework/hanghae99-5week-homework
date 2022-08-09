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

    private final SubCommentService subcommentService;


    @RequestMapping(value = "/api/auth/subcomment", method = RequestMethod.POST)
    public ResponseDto<?> createComment(@RequestBody SubCommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return subcommentService.createSubComment(requestDto, request);
    }

    @RequestMapping(value = "/api/subcomment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllComments(@PathVariable Long id) {
        return subcommentService.getAllSubCommentsByComment(id);
    }

    @RequestMapping(value = "/api/auth/subcomment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody SubCommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return subcommentService.updateSubComment(id, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/subcomment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long id,
                                        HttpServletRequest request) {
        return subcommentService.deleteSubComment(id, request);
    }
}
