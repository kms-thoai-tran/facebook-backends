package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;

import java.util.UUID;

public interface IPostService {
    PostResponse create(PostRequest postRequest);

    SuccessResponse update(UUID id, PostRequest postRequest);

    SuccessResponse delete(UUID postId);

    PostResponse getPostById(UUID id);
}
