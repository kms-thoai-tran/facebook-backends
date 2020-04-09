package com.example.facebookbackend.dto.response;

import com.example.facebookbackend.model.PostComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse extends SuccessResponse {
    UUID id;
    String text;
    Map<String, String> tags;
    List<String> images;
    Map<String, Map<String, String>> facebookLikes;
    List<PostComment> postComments;
}
