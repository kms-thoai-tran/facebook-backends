package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("v1/users")
public interface IImageController {
    @PostMapping("/uploadFile")
    ResponseEntity<SuccessResponse> uploadFile(@RequestPart(value = "file") MultipartFile files);
}
