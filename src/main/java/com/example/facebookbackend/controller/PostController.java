package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.PostCommentRequest;
import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.PostComment;
import com.example.facebookbackend.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class PostController implements IPostController {

    @Autowired
    IPostService postService;

    @Override
    public ResponseEntity<Mono<PostResponse>> getPostById(@Valid UUID id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @Override
    public ResponseEntity<Mono<PostResponse>> create(PostRequest postRequest) {
        return ResponseEntity.ok().body(postService.create(postRequest));
    }

    @Override
    public ResponseEntity<Mono<SuccessResponse>> update(@Valid UUID id, @Valid PostRequest postRequest) {
        return ResponseEntity.ok().body(postService.update(id, postRequest));
    }

    @Override
    public ResponseEntity<SuccessResponse> delete(@Valid UUID postId) {
        return ResponseEntity.ok().body(postService.delete(postId));
    }

    @Override
    public ResponseEntity<Mono<PostComment>> createComment(@Valid UUID id, PostCommentRequest postCommentRequest) {
        return ResponseEntity.ok().body(postService.createComment(id, postCommentRequest));
    }

    @Override
    public ResponseEntity<Mono<PostComment>> updateComment(@Valid UUID id, PostCommentRequest postCommentRequest) {
        return ResponseEntity.ok().body(postService.updateComment(id, postCommentRequest));
    }

    @Override
    public ResponseEntity<SuccessResponse> deleteComment(@Valid UUID postId, PostCommentRequest postCommentRequest) {
        return ResponseEntity.ok().body(postService.deleteComment(postId, postCommentRequest.getCommentId()));
    }
}
