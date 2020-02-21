package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class PostController implements IPostController {

    @Autowired
    IPostService postService;

    @Override
    public ResponseEntity<PostResponse> getPostById(@Valid UUID id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @Override
    public ResponseEntity<PostResponse> create(PostRequest postRequest) {
        return ResponseEntity.ok().body(postService.create(postRequest));
    }

    @Override
    public ResponseEntity<SuccessResponse> update(@Valid UUID id, @Valid PostRequest postRequest) {
        return ResponseEntity.ok().body(postService.update(id, postRequest));
    }

    @Override
    public ResponseEntity<SuccessResponse> delete(@Valid UUID postId) {
        return ResponseEntity.ok().body(postService.delete(postId));
    }
}
