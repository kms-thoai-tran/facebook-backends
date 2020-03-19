package com.example.facebookbackend.dto.request;


import com.example.facebookbackend.util.FacebookLikeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostRequest {
    String text;
    Set<UUID> tagIds;
    Set<String> imageIds;
    Map<String, FacebookLikeType> facebookLikes;
    Map<String, String> comments;
}

