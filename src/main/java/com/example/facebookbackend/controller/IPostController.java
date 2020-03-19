package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.PostCommentRequest;
import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.PostComment;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RequestMapping(path = "v1/users/post")
public interface IPostController {

    @RequestMapping(path = "/{id}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    ResponseEntity<Mono<PostResponse>> getPostById(@PathVariable("id") @Valid UUID id);

    @PostMapping()
    ResponseEntity<Mono<PostResponse>> create(@RequestBody PostRequest postRequest);

    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    ResponseEntity<Mono<SuccessResponse>> update(@PathVariable("id") @Valid UUID id, @Valid @RequestBody PostRequest postRequest);

    @RequestMapping(path = "/{id}", method = {RequestMethod.DELETE, RequestMethod.OPTIONS})
    ResponseEntity<SuccessResponse> delete(@PathVariable("id") @Valid UUID postId);

    @RequestMapping(path = "/{id}/comment", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    ResponseEntity<Mono<PostComment>> createComment(@PathVariable("id") @Valid UUID id, @RequestBody PostCommentRequest postCommentRequest);

    @RequestMapping(path = "/{id}/comment", method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    ResponseEntity<Mono<PostComment>> updateComment(@PathVariable("id") @Valid UUID id, @RequestBody PostCommentRequest postCommentRequest);

    @RequestMapping(path = "/{id}/comment", method = {RequestMethod.DELETE, RequestMethod.OPTIONS})
    ResponseEntity<SuccessResponse> deleteComment(@PathVariable("id") @Valid UUID postId, @RequestBody PostCommentRequest postCommentRequest);
}
