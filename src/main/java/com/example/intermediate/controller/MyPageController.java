package com.example.intermediate.controller;

import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.repository.LikeRepository;
import com.example.intermediate.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/api/auth/mypage")
    public ResponseDto<?> getAllContentsByMember(HttpServletRequest request) {
        return myPageService.getAllContentsByMember(request);
    }

}
