package com.example.facebookbackend.service.mapper;

import com.example.facebookbackend.model.PostComment;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

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
        return postCommentBuilder.build();
    }
}
