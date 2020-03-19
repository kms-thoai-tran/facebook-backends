package com.example.facebookbackend.service.mapper;

import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.model.Post;
import com.example.facebookbackend.model.PostComment;
import com.example.facebookbackend.util.FacebookLikeType;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;

import java.util.*;
import java.util.stream.Collectors;

public class PostMapper {
    public static Map<String, AttributeValue> toMapCreate(Post post) {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put("PK", AttributeValue.builder().s(String.join("#", "POST", post.getId().toString())).build());
        map.put("SK", AttributeValue.builder().s(String.join("#", "USER", post.getUserId().toString())).build());
        if (post.getText() != null) {
            map.put("message", AttributeValue.builder().s(post.getText()).build());
        }
        if (post.getTagIds() != null) {
            map.put("tagIds", AttributeValue.builder().ss(post.getTagIds().stream().map(x -> x.toString()).collect(Collectors.toList())).build());
        }
        return map;
    }

    public static PostResponse fromMap(Map<String, AttributeValue> attributeValueMap, List<PostComment> comments) {
        PostResponse postResponse = new PostResponse();
        if (attributeValueMap.size() == 0) {
            return postResponse;
        }

        if (attributeValueMap.get("message") != null) {
            postResponse.setText(attributeValueMap.get("message").s());
        }
        if (attributeValueMap.get("tagIds") != null) {
            Set<UUID> tagIds = attributeValueMap.get("tagIds").ss().stream().map(x -> UUID.fromString(x)).collect(Collectors.toSet());
            postResponse.setTagIds(tagIds);
        }
        if (attributeValueMap.get("facebookLikes") != null) {
            Map<String, FacebookLikeType> facebookLikes = new HashMap<>();

            attributeValueMap.get("facebookLikes").m().entrySet().stream().forEach(x -> {
                facebookLikes.put(x.getKey(), FacebookLikeType.valueOf(x.getValue().s()));
            });
            postResponse.setFacebookLikes(facebookLikes);
        }
        postResponse.setId(UUID.fromString(attributeValueMap.get("PK").s().split("#")[1]));
        postResponse.setPostComments(comments);
        return postResponse;
    }

    public static Map<String, AttributeValueUpdate> toMapUpdate(PostRequest postRequest) {
        Map<String, AttributeValueUpdate> map = new HashMap<>();

        if (postRequest.getText() != null) {
            map.put("message", AttributeValueUpdate.builder().value(AttributeValue.builder().s(postRequest.getText()).build()).action(AttributeAction.PUT).build());
        }
        if (postRequest.getTagIds() != null) {
            map.put("tagIds", AttributeValueUpdate.builder().value(AttributeValue.builder().ss(postRequest.getTagIds().stream().map(x -> x.toString()).collect(Collectors.toList())).build()).action(AttributeAction.PUT).build());
        }
        if (postRequest.getFacebookLikes() != null) {
            Map<String, AttributeValue> likes = new HashMap<>();
            postRequest.getFacebookLikes().entrySet().stream().forEach(like -> likes.put(like.getKey(), AttributeValue.builder().s(like.getValue().toString()).build()));
            map.put("facebookLikes", AttributeValueUpdate.builder().value(AttributeValue.builder().m(likes).build()).build());
        }
//        if (postRequest.getComments() != null) {
//            Map<String, AttributeValue> comments = new HashMap<>();
//            postRequest.getComments().entrySet().stream().forEach( comment -> comments.put(comment.getKey(), AttributeValue.builder().s(comment.getValue()).build()));
//            map.put("comments", AttributeValueUpdate.builder().value(AttributeValue.builder().m(comments).build()).build());
//        }
        return map;
    }

}
