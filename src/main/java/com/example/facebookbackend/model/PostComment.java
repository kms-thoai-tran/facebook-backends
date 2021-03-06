package com.example.facebookbackend.model;

import com.example.facebookbackend.util.FacebookLikeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostComment {
    String commentId;
    String comment;
    UUID userId;
    Map<String, FacebookLikeType> facebookLikes;
}
