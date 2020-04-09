package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.FriendRequestRequest;
import com.example.facebookbackend.dto.response.FriendRequestResponse;
import com.example.facebookbackend.model.FriendRequest;
import com.example.facebookbackend.service.mapper.FriendRequestMapper;
import com.example.facebookbackend.util.FriendRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class FriendRequestService extends BaseService implements IFriendRequestService {
    @Autowired
    IDynamoDbService dynamoDbService;

    @Override
    public Mono<List<FriendRequestResponse>> getFriendRequests(String status) {
        Map<String, AttributeValue> attrValues = new HashMap<>();
//        attrValues.put(":PK", AttributeValue.builder().s(String.join("#", "USER", getCurrentUserId().toString())).build());
        attrValues.put(":PK", AttributeValue.builder().s(String.join("#", "USER", "c61744ed-3360-44b8-b0ad-0d6b3497b432")).build());
        attrValues.put(":SK", AttributeValue.builder().s("FRIEND#").build());
        QueryRequest queryRequest = QueryRequest.builder()
                .tableName("facebook")
                .keyConditionExpression("PK=:PK and begins_with(SK, :SK)")
//                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();
        CompletableFuture<List<Map<String, AttributeValue>>> query = dynamoDbService.query(queryRequest);
        return Mono.fromCompletionStage(query).map(FriendRequestMapper::fromMapList);
    }

    @Override
    public Mono<FriendRequestResponse> createFriendRequest(FriendRequestRequest friendRequestRequest) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "USER", friendRequestRequest.getFriendId().toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "FRIEND", getCurrentUserId().toString())).build());
        attributeValueHashMap.put("status", AttributeValue.builder().s(FriendRequestStatus.NEW.toString()).build());
        CompletableFuture<Map<String, AttributeValue>> result = dynamoDbService.putItem(attributeValueHashMap);
        return Mono.fromCompletionStage(result).map(FriendRequestMapper::fromMap);
//        return Mono.fromCompletionStage(result).map(FriendRequestMapper:: fromMap);
    }

    @Override
    public Mono<FriendRequestResponse> acceptFriendRequest(FriendRequestRequest friendRequestRequest) {
        FriendRequest friendRequest = FriendRequest.builder()
                .friendId(friendRequestRequest.getFriendId())
                .status(FriendRequestStatus.ACCEPT).build();
        return updateFriendRequest(friendRequest);
    }

    @Override
    public Mono<FriendRequestResponse> rejectFriendRequest(FriendRequestRequest friendRequestRequest) {
        FriendRequest friendRequest = FriendRequest.builder()
                .friendId(friendRequestRequest.getFriendId())
                .status(FriendRequestStatus.REJECT).build();
        return updateFriendRequest(friendRequest);
    }

    private Mono<FriendRequestResponse> updateFriendRequest(FriendRequest friendRequest) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
//        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "USER", getCurrentUserId().toString())).build());
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "USER", "c61744ed-3360-44b8-b0ad-0d6b3497b432")).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "FRIEND", friendRequest.getFriendId().toString())).build());

        Map<String, AttributeValueUpdate> updatedValue = new HashMap<>();
        updatedValue.put("status", AttributeValueUpdate.builder().value(AttributeValue.builder().s(friendRequest.getStatus().toString()).build()).build());
        CompletableFuture<Map<String, AttributeValue>> result = dynamoDbService.updateItem(attributeValueHashMap, updatedValue);
        return Mono.fromCompletionStage(result).map(FriendRequestMapper::fromMap);
    }
}
