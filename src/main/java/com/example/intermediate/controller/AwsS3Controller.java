package com.example.intermediate.controller;

import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AwsS3Controller {

    private final AwsS3Service s3Uploader;

    @ExceptionHandler
    @PostMapping("/api/upload/image")
    @ResponseBody
    public ResponseDto<?> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if(multipartFile==null) {
            return ResponseDto.fail("EMPTY", "multipart file is empty");
        }
        String imgUrl = null;
        try {
            imgUrl = s3Uploader.upload(multipartFile, "static");
        }
        catch (IOException E) {
            return ResponseDto.fail("EMPTY", "multipart file is empty");
        }
        return ResponseDto.success(imgUrl);
    }
}

