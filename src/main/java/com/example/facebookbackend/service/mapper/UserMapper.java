package com.example.facebookbackend.service.mapper;

import com.example.facebookbackend.dto.response.UserResponse;
import com.example.facebookbackend.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserMapper {

    public static Map<String, AttributeValue> toMapGet(User user) {
        Map<String, AttributeValue> map = new HashMap<>();
        map.put("PK", AttributeValue.builder().s(String.join("#", "USER", user.getId().toString())).build());
        map.put("SK", AttributeValue.builder().s(user.getEmail()).build());
        map.put("enable", AttributeValue.builder().bool(user.isEnabled()).build());
        map.put("password", AttributeValue.builder().s(new BCryptPasswordEncoder().encode(user.getPassword())).build());
        return map;
    }

    public static UserResponse fromMap(Map<String, AttributeValue> attributeValueMap) {
        UserResponse postResponse = new UserResponse();
        if (attributeValueMap.size() == 0) {
            return postResponse;
        }
        postResponse.setEmail(attributeValueMap.get("SK").s());
        postResponse.setId(UUID.fromString(attributeValueMap.get("PK").s().split("#")[1]));
        return postResponse;
    }

    public static User convertByUserPrincipal(Map<String, AttributeValue> attributeValueMap) {
        if (attributeValueMap.size() == 0) {
            return null;
        }
        User user = new User();
        user.setEmail(attributeValueMap.get("SK").s());
        user.setPassword(attributeValueMap.get("password").s());
        user.setId(UUID.fromString(attributeValueMap.get("PK").s().split("#")[1]));
        return user;

    }
}
