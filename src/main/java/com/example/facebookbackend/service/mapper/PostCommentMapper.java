package com.example.facebookbackend.service.mapper;

import com.example.facebookbackend.model.PostComment;
import com.example.facebookbackend.util.FacebookLikeType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostCommentMapper {
    public static PostComment fromMap(Map<String, AttributeValue> attributeValueMap) {
        PostComment.PostCommentBuilder postCommentBuilder = PostComment.builder();

        if (attributeValueMap.get("comment") != null) {
            postCommentBuilder.comment(attributeValueMap.get("comment").s());
        }
        if (attributeValueMap.get("SK") != null) {
            postCommentBuilder.commentId(attributeValueMap.get("SK").s());
        }
        if (attributeValueMap.get("userId") != null) {
            postCommentBuilder.userId(UUID.fromString(attributeValueMap.get("userId").s().split("#")[1]));
        }
        if (attributeValueMap.get("facebookLikes") != null) {
            Map<String, FacebookLikeType> facebookLikes = new HashMap<>();

            attributeValueMap.get("facebookLikes").m().entrySet().stream().forEach(x -> {
                facebookLikes.put(x.getKey(), FacebookLikeType.valueOf(x.getValue().s()));
            });
            postCommentBuilder.facebookLikes(facebookLikes);
        }
        return postCommentBuilder.build();
    }
}
