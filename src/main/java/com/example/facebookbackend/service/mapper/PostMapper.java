package com.example.facebookbackend.service.mapper;

import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.model.Post;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostMapper {
    public static Map<String, AttributeValue> toMapGet(Post post) {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put("PK", AttributeValue.builder().s(String.join("#", "USER", post.getUserId().toString())).build());
        map.put("SK", AttributeValue.builder().s(String.join("#", "POST", post.getId().toString())).build());
        if (post.getText() != null) {
            map.put("text", AttributeValue.builder().s(post.getText()).build());
        }
        return map;
    }

    public static PostResponse fromMap(Map<String, AttributeValue> attributeValueMap) {
        PostResponse postResponse = new PostResponse();
        if (attributeValueMap.size() == 0) {
            return postResponse;
        }
        String test = attributeValueMap.get("SK").s().split("#")[1];
        postResponse.setText(attributeValueMap.get("text").s());
        postResponse.setId(UUID.fromString(test));
        return postResponse;
    }

    public static Map<String, AttributeValueUpdate> toMapUpdate(Post post) {
        Map<String, AttributeValueUpdate> map = new HashMap<>();
        if (post.getText() != null) {
            map.put("text", AttributeValueUpdate.builder().value(AttributeValue.builder().s(post.getText()).build()).action(AttributeAction.PUT).build());
        }
        return map;
    }

}
