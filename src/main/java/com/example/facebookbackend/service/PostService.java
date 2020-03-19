package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.PostCommentRequest;
import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.dto.response.UserResponse;
import com.example.facebookbackend.model.Post;
import com.example.facebookbackend.model.PostComment;
import com.example.facebookbackend.repository.IFacebookLikeRepository;
import com.example.facebookbackend.service.mapper.PostCommentMapper;
import com.example.facebookbackend.service.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class PostService extends BaseService implements IPostService {

    @Autowired
    IFacebookLikeRepository facebookLikeRepository;

    @Autowired
    IDynamoDbService dynamoDbService;

    @Override
    public Mono<PostResponse> create(PostRequest postRequest) {
        validatePostRequest(postRequest);
        Post post = Post.builder()
                .id(UUID.randomUUID())
                .userId(getCurrentUserId())
                .tagIds(postRequest.getTagIds())
                .text(postRequest.getText())
                .build();
        Map<String, AttributeValue> postItems = PostMapper.toMapCreate(post);


        CompletableFuture<Map<String, AttributeValue>> result = dynamoDbService.putItem(postItems);
        return Mono.fromCompletionStage(result).map(x -> PostMapper.fromMap(x, null));
    }

    @Override
    public Mono<SuccessResponse> update(UUID id, PostRequest postRequest) {
        validatePostRequest(postRequest);

        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", id.toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "USER", getCurrentUserId().toString())).build());
        Map<String, AttributeValueUpdate> updatedValue = PostMapper.toMapUpdate(postRequest);
        CompletableFuture<Map<String, AttributeValue>> a = dynamoDbService.updateItem(attributeValueHashMap, updatedValue);
        return Mono.fromCompletionStage(a).map(x -> PostMapper.fromMap(x, null));
    }

    @Override
    public SuccessResponse delete(UUID postId) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", postId.toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "USER", getCurrentUserId().toString())).build());

        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .key(attributeValueHashMap)
                .tableName("facebook")
                .build();
        dynamoDbService.deleteItem(deleteItemRequest);
        return new SuccessResponse();
    }

    @Override
    public Mono<PostResponse> getPostById(UUID id) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", id.toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "USER", getCurrentUserId().toString())).build());
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("facebook")
                .key(attributeValueHashMap)
                .build();
        CompletableFuture<Map<String, AttributeValue>> mapCompletableFuture = dynamoDbService.getItem(getItemRequest);
        List<PostComment> postComments = getComments(id);
        PostResponse postResponse = PostMapper.fromMap(mapCompletableFuture.join(), postComments);
//        return Mono.just(postResponse);
        return Mono.fromCompletionStage(mapCompletableFuture).map(x -> PostMapper.fromMap(x, postComments));
    }

    private void validatePostRequest(PostRequest postRequest) {

    }

    private void createFacebookLikes(UUID postId, List<UserResponse> userResponses, Set<UUID> facebookLikeIds) {
        Map<String, Map<String, AttributeValue>> attributeValueHashMapResult = new HashMap<>();
        facebookLikeIds.stream().forEach(tagId -> {
            Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
            attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", postId.toString())).build());
            attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "LIKE", tagId.toString())).build());
            attributeValueHashMapResult.put(tagId.toString(), attributeValueHashMap);
        });

        Map<String, List<WriteRequest>> requestItems = new HashMap<>();
        List<WriteRequest> writeRequests = facebookLikeIds.stream().map(x ->
                WriteRequest.builder().putRequest(PutRequest.builder()
                        .item(attributeValueHashMapResult.get(x.toString())).build())
                        .build())
                .collect(Collectors.toList());

        requestItems.put("facebook", writeRequests);
        BatchWriteItemRequest batchWriteItemRequest = BatchWriteItemRequest.builder()
                .requestItems(requestItems)
                .build();
        dynamoDbService.batchWriteItem(batchWriteItemRequest);

    }

    @Override
    public Mono<PostComment> createComment(UUID postId, PostCommentRequest postCommentRequest) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        String commentId = postCommentRequest.getCommentId() != null ?
                String.join("#", postCommentRequest.getCommentId(), UUID.randomUUID().toString()) :
                String.join("#", "COMMENT", UUID.randomUUID().toString());
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", postId.toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(commentId).build());
        attributeValueHashMap.put("userId", AttributeValue.builder().s(String.join("#", "USER", postCommentRequest.getUserId().toString())).build());
        attributeValueHashMap.put("comment", AttributeValue.builder().s(postCommentRequest.getComment()).build());
        CompletableFuture<Map<String, AttributeValue>> updateItem = dynamoDbService.putItem(attributeValueHashMap);
        return Mono.fromCompletionStage(updateItem).map(PostCommentMapper::fromMap);
    }

    public List<PostComment> getComments(UUID postId) {
        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":PK", AttributeValue.builder().s(String.join("#", "POST", postId.toString())).build());
        attrValues.put(":SK", AttributeValue.builder().s("COMMENT#").build());
        QueryRequest queryRequest = QueryRequest.builder()
                .tableName("facebook")
                .keyConditionExpression("PK=:PK and begins_with(SK, :SK)")
//                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();
        CompletableFuture<List<Map<String, AttributeValue>>> query = dynamoDbService.query(queryRequest);
        return query.join().stream().map(PostCommentMapper::fromMap).collect(Collectors.toList());
    }

    @Override
    public Mono<PostComment> updateComment(UUID postId, PostCommentRequest postCommentRequest) {
        // need to validate this commentId valid

        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", postId.toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(postCommentRequest.getCommentId()).build());
        Map<String, AttributeValueUpdate> updatedValue = new HashMap<>();
        updatedValue.put("userId", AttributeValueUpdate.builder().value(AttributeValue.builder().s(String.join("#", "USER", postCommentRequest.getUserId().toString())).build()).build());
        updatedValue.put("comment", AttributeValueUpdate.builder().value(AttributeValue.builder().s(postCommentRequest.getComment()).build()).build());
        CompletableFuture<Map<String, AttributeValue>> updateItem = dynamoDbService.updateItem(attributeValueHashMap, updatedValue);
        return Mono.fromCompletionStage(updateItem).map(PostCommentMapper::fromMap);
    }

    @Override
    public SuccessResponse deleteComment(UUID postId, String commentId) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "POST", postId.toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(commentId).build());

        DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                .key(attributeValueHashMap)
                .tableName("facebook")
                .build();
        dynamoDbService.deleteItem(deleteItemRequest);
        return new SuccessResponse();
    }

}
