package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.PostCommentRequest;
import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.PostComment;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IPostService {
    Mono<PostResponse> create(PostRequest postRequest);

    Mono<SuccessResponse> update(UUID id, PostRequest postRequest);

    SuccessResponse delete(UUID postId);

    Mono<PostResponse> getPostById(UUID id);

    Mono<PostComment> createComment(UUID id, PostCommentRequest postCommentRequest);

    Mono<PostComment> updateComment(UUID id, PostCommentRequest postCommentRequest);

    SuccessResponse deleteComment(UUID postId, String comment);
}
