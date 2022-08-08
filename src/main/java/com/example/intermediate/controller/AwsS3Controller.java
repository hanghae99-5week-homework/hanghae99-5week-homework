package com.example.intermediate.controller;

import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class AwsS3Controller {

    private final AwsS3Service s3Uploader;

    @PostMapping("/api/upload/image")
    @ResponseBody
    public ResponseDto<?> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String imgUrl = s3Uploader.upload(multipartFile, "static");
        return ResponseDto.success(imgUrl);
    }
}
