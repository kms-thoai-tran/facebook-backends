package com.example.facebookbackend.dto.response;

import com.example.facebookbackend.model.PostComment;
import com.example.facebookbackend.util.FacebookLikeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse extends SuccessResponse {
    UUID id;
    String text;
    Set<UUID> tagIds;
    Set<String> imageIds;
    Map<String, FacebookLikeType> facebookLikes;
    List<PostComment> postComments;
}
