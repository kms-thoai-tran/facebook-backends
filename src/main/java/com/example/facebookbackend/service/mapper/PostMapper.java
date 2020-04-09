package com.example.facebookbackend.service.mapper;

import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.model.Post;
import com.example.facebookbackend.model.PostComment;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PostMapper {
    public static Map<String, AttributeValue> toMapCreate(Post post) {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put("PK", AttributeValue.builder().s(String.join("#", "POST", post.getId().toString())).build());
        map.put("SK", AttributeValue.builder().s(String.join("#", "USER", post.getUserId().toString())).build());
        if (post.getText() != null) {
            map.put("message", AttributeValue.builder().s(post.getText()).build());
        }
        if (post.getImages() != null) {
            map.put("images", AttributeValue.builder().ss(post.getImages()).build());
        }
        if (post.getTags().size() > 0) {
            map.put("tags", AttributeValue.builder().m(post.getTags()).build());
        }
        map.put("facebookLikes", AttributeValue.builder().m(new HashMap<>()).build());
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
        if (attributeValueMap.get("tags") != null) {
            Map<String, AttributeValue> tagsMap = attributeValueMap.get("tags").m();
            Map<String, String> tags = new HashMap<>();
            tagsMap.entrySet().stream().forEach(tag -> tags.put(tag.getKey(), tag.getValue().s()));
            postResponse.setTags(tags);
        }
        if (attributeValueMap.get("facebookLikes") != null) {
            Map<String, Map<String, String>> facebookLikes = new HashMap<>();
            attributeValueMap.get("facebookLikes").m().entrySet().stream().forEach(x -> {
                Map<String, String> object1 = new HashMap<>();
                x.getValue().m().entrySet().stream().forEach(ob -> object1.put(ob.getKey(), ob.getValue().s()));
                facebookLikes.put(x.getKey(), object1);
            });
            postResponse.setFacebookLikes(facebookLikes);
        }
        if (attributeValueMap.get("images") != null) {
            postResponse.setImages(attributeValueMap.get("images").ss());
        }
        postResponse.setId(UUID.fromString(attributeValueMap.get("PK").s().split("#")[1]));
        postResponse.setPostComments(comments);
        return postResponse;
    }

    public static Map<String, AttributeValueUpdate> toMapUpdate(Post post) {
        Map<String, AttributeValueUpdate> map = new HashMap<>();

        if (post.getText() != null) {
            map.put("message", AttributeValueUpdate.builder().value(AttributeValue.builder().s(post.getText()).build()).action(AttributeAction.PUT).build());
        }
        if (post.getTags() != null) {
            map.put("tags", AttributeValueUpdate.builder().value(AttributeValue.builder().m(post.getTags()).build()).action(AttributeAction.PUT).build());
        }
//        if (postRequest.getFacebookLikes() != null) {
//            Map<String, AttributeValue> likes = new HashMap<>();
//            postRequest.getFacebookLikes().entrySet().stream().forEach(like -> likes.put(like.getKey(), AttributeValue.builder().s(like.getValue().toString()).build()));
//            map.put("facebookLikes", AttributeValueUpdate.builder().value(AttributeValue.builder().m(likes).build()).build());
//        }
//        if (postRequest.getComments() != null) {
//            Map<String, AttributeValue> comments = new HashMap<>();
//            postRequest.getComments().entrySet().stream().forEach( comment -> comments.put(comment.getKey(), AttributeValue.builder().s(comment.getValue()).build()));
//            map.put("comments", AttributeValueUpdate.builder().value(AttributeValue.builder().m(comments).build()).build());
//        }
        if (post.getImages() != null) {
            map.put("images", AttributeValueUpdate.builder().value(AttributeValue.builder().ss(post.getImages()).build()).action(AttributeAction.PUT).build());
        }
        return map;
    }

}
