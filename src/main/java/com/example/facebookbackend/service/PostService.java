package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;
import com.example.facebookbackend.model.Post;
import com.example.facebookbackend.model.User;
import com.example.facebookbackend.repository.IFacebookLikeRepository;
import com.example.facebookbackend.repository.IUserRepository;
import com.example.facebookbackend.service.mapper.PostMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class PostService extends BaseService implements IPostService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IFacebookLikeRepository facebookLikeRepository;

    @Autowired
    IDynamoDbService dynamoDbService;

    @Override
    public Mono<PostResponse> create(PostRequest postRequest) {
//        Optional<User> user = userRepository.findById(getCurrentUserId());
//        validatePostRequest(postRequest);
//        Post post = updatePost(new Post(), postRequest);
//        post.setCreatedBy(getCurrentUser().getId());
//        post.setUser(user.get());
//        Post result = postRepository.save(post);
//        return PostResponse.convertFrom(result);
        Optional<User> user = userRepository.findById(getCurrentUserId());
        validatePostRequest(postRequest);
        CompletableFuture<Map<String, AttributeValue>> a = dynamoDbService.putItem(PostMapper.toMapGet(Post.builder()
                .id(UUID.randomUUID())
                .userId(getCurrentUserId())
                .text(postRequest.getText())
                .build()));
        return Mono.fromCompletionStage(a).map(PostMapper::fromMap);
    }

    @Override
    public Mono<SuccessResponse> update(UUID id, PostRequest postRequest) {
//        validatePostRequest(postRequest);

        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "USER", getCurrentUserId().toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "POST", id.toString())).build());

        Map<String, AttributeValueUpdate> updatedValue = PostMapper.toMapUpdate(Post.builder()
                .text(postRequest.getText())
                .build());
        CompletableFuture<Map<String, AttributeValue>> a = dynamoDbService.updateItem(attributeValueHashMap, updatedValue);
        return Mono.fromCompletionStage(a).map(PostMapper::fromMap);
    }

    @Override
    public SuccessResponse delete(UUID postId) {
//        Optional<Post> postOpt = postRepository.findById(postId);
//        if (postOpt.isPresent()) {
//            postRepository.delete(postOpt.get());
//            return new SuccessResponse();
//        }
        throw new RuntimeException("cannot delete the post");
    }

    @Override
    public Mono<PostResponse> getPostById(UUID id) {
        Map<String, AttributeValue> attributeValueHashMap = new HashMap<>();
        attributeValueHashMap.put("PK", AttributeValue.builder().s(String.join("#", "USER", getCurrentUserId().toString())).build());
        attributeValueHashMap.put("SK", AttributeValue.builder().s(String.join("#", "POST", id.toString())).build());
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName("facebook")
                .key(attributeValueHashMap)
                .build();
        CompletableFuture<Map<String, AttributeValue>> mapCompletableFuture = dynamoDbService.findByItemRequest(getItemRequest);
        return Mono.fromCompletionStage(mapCompletableFuture).map(PostMapper::fromMap);

    }

    private void validatePostRequest(PostRequest postRequest) {
    }

    private Post updatePost(Post post, PostRequest postRequest) {
        if (!Strings.isBlank(postRequest.getText())) {
            post.setText(postRequest.getText());
        }
        if (postRequest.getTagIds() != null) {
            List<User> users = userRepository.findByIdIn(postRequest.getTagIds());
//            post.setTags(users.stream().collect(Collectors.toSet()));
        }

        if (postRequest.getImageIds() != null) {

        }
        if (postRequest.getFacebookLikeIds() != null) {
            List<FacebookLike> likes = facebookLikeRepository.findByIdIn(postRequest.getFacebookLikeIds());
//            post.setFacebookLikes(likes.stream().collect(Collectors.toSet()));
        }
        return post;
    }
}
