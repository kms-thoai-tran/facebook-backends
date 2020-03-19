package com.example.facebookbackend.service.mapper;

import com.example.facebookbackend.dto.response.FriendRequestResponse;
import com.example.facebookbackend.util.FriendRequestStatus;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FriendRequestMapper {
    public static FriendRequestResponse fromMap(Map<String, AttributeValue> attributeValueMap) {
        FriendRequestResponse.FriendRequestResponseBuilder requestResponseBuilder = FriendRequestResponse.builder();
        if (attributeValueMap.get("SK") != null) {
            requestResponseBuilder.friendId(UUID.fromString(attributeValueMap.get("SK").s().split("#")[1]));
        }
        if (attributeValueMap.get("status") != null) {
            requestResponseBuilder.status(FriendRequestStatus.valueOf(attributeValueMap.get("status").s()));
        }
        return requestResponseBuilder.build();
    }

    public static List<FriendRequestResponse> fromMapList(List<Map<String, AttributeValue>> mapList) {
        return mapList.stream().map(x -> fromMap(x)).collect(Collectors.toList());
    }
}
