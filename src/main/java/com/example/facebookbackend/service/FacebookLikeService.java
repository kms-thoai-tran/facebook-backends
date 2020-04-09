package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.FacebookLikeRequest;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;
import com.example.facebookbackend.repository.IFacebookLikeRepository;
import com.example.facebookbackend.util.FacebookLikeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class FacebookLikeService extends BaseService implements IFacebookLikeService {

    @Autowired
    IFacebookLikeRepository facebookLikeRepository;

    @Autowired
    IDynamoDbService dynamoDbService;

    @Override
    public SuccessResponse syncUp() {
        List<FacebookLike> likes = new ArrayList<>();
        likes.add(new FacebookLike(FacebookLikeType.Like));
        likes.add(new FacebookLike(FacebookLikeType.Love));
        likes.add(new FacebookLike(FacebookLikeType.Haha));
        likes.add(new FacebookLike(FacebookLikeType.Wow));
        likes.add(new FacebookLike(FacebookLikeType.Angry));
        facebookLikeRepository.saveAll(likes);
        return new SuccessResponse();
    }

    @Override
    public List<FacebookLike> getAll() {
        return facebookLikeRepository.findAll();
    }

    @Override
    public Mono<SuccessResponse> likePost(FacebookLikeRequest request) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", request.getPostId().toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "USER", request.getUserId().toString())).build());

        UpdateItemRequest updateItemRequest = getUpdateItemRequest(request, attributeValueHashMap);
        CompletableFuture<Map<String, AttributeValue>> test = dynamoDbService.updateItem(updateItemRequest);
        return Mono.just(new SuccessResponse());
    }

    private UpdateItemRequest getUpdateItemRequest(FacebookLikeRequest request, Map<String, AttributeValue> attributeValueHashMap) {
        Map<String, String> attributeNames = new HashMap<>();
        attributeNames.put("#user", getCurrentUserId().toString());
        UpdateItemRequest.Builder builder = UpdateItemRequest.builder()
                .tableName("facebook")
                .key(attributeValueHashMap)
                .returnValues(ReturnValue.UPDATED_NEW)
                .expressionAttributeNames(attributeNames);

        if (request.getType().equals(FacebookLikeType.None)) {
            builder.updateExpression("REMOVE facebookLikes.#user");
        } else {
            Map<String, AttributeValue> updatedValue = new HashMap<>();
            Map<String, AttributeValue> data = new HashMap<>();
            data.put("type", AttributeValue.builder().s(request.getType().toString()).build());
            data.put("userName", AttributeValue.builder().s(getCurrentUser().getName()).build());
            updatedValue.put(":item", AttributeValue.builder().m(data).build());
            builder.updateExpression("SET facebookLikes.#user = :item");
            builder.expressionAttributeValues(updatedValue);
        }
        return builder.build();
    }

    @Override
    public Mono<SuccessResponse> likeComment(FacebookLikeRequest request) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", request.getPostId().toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(request.getCommentId()).build());
        UpdateItemRequest updateItemRequest = getUpdateItemRequest(request, attributeValueHashMap);
        dynamoDbService.updateItem(updateItemRequest);
        return Mono.just(new SuccessResponse());
    }
}
