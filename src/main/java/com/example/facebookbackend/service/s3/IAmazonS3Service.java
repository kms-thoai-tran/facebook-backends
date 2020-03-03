package com.example.facebookbackend.service.s3;

import com.example.facebookbackend.dto.response.SuccessResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAmazonS3Service {
    SuccessResponse uploadMultipleFiles(List<MultipartFile> files);
}
