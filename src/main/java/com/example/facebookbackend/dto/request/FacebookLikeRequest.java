package com.example.facebookbackend.dto.request;

import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.util.FacebookLikeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacebookLikeRequest extends SuccessResponse {
    UUID postId;
    UUID userId;
    String commentId;
    FacebookLikeType type;
}
