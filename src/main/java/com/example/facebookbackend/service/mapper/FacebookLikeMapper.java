package com.example.facebookbackend.service.mapper;


import com.example.facebookbackend.dto.response.FacebookLikeResponse;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

public class FacebookLikeMapper {
    public static FacebookLikeResponse fromMap(Map<String, AttributeValue> attributeValueMap) {
        FacebookLikeResponse.FacebookLikeResponseBuilder builder = FacebookLikeResponse.builder();
        if (attributeValueMap.get("facebookLikes") != null) {

        }

        return builder.build();

    }
}
