package com.example.intermediate.controller;

import com.example.intermediate.FileTypeErrorException;
import com.example.intermediate.dto.response.ResponseDto;
import com.example.intermediate.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RestControllerAdvice
public class AwsS3Controller {

    private final AwsS3Service s3Uploader;

    @PostMapping("/api/upload/image")
    @ResponseBody
    public ResponseDto<?> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return ResponseDto.fail("EMPTY", "multipart file is empty");
        }
        String imgUrl = s3Uploader.upload(multipartFile, "static");
        return ResponseDto.success(imgUrl);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseDto<?> handlingException(){
        return ResponseDto.fail("CONVERT_FAIL", "fail convert multipart to file");
    }

    @ExceptionHandler(FileTypeErrorException.class)
    public ResponseDto<?> handlingFileTypeErrorException(){
        return ResponseDto.fail("FILE_TYPE_ERROR", "이미지가 아닙니다.");
    }
}

