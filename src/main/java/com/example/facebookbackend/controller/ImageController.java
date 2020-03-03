package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.service.s3.IAmazonS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageController implements IImageController {

    @Autowired
    IAmazonS3Service amazonS3Service;

    @Override
    public ResponseEntity<SuccessResponse> uploadFile(MultipartFile files) {
        List<MultipartFile> files1 = new ArrayList<>();
        files1.add(files);
        return ResponseEntity.ok().body(amazonS3Service.uploadMultipleFiles(files1));
    }
}
