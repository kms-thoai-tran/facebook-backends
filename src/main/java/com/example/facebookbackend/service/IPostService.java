package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IPostService {
    Mono<PostResponse> create(PostRequest postRequest);

    Mono<SuccessResponse> update(UUID id, PostRequest postRequest);

    SuccessResponse delete(UUID postId);

    Mono<PostResponse> getPostById(UUID id);
}
