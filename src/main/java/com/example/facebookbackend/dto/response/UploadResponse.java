package com.example.facebookbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UploadResponse extends SuccessResponse {
    Set<String> keys;
}
