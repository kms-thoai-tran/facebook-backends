package com.example.facebookbackend.dto.response;

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
public class FacebookLikeResponse {
    UUID id;
    UUID postId;
    UUID commentId;
    FacebookLikeType type;
}
