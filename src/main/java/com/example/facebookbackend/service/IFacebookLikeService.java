package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.FacebookLikeRequest;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IFacebookLikeService {
    SuccessResponse syncUp();

    List<FacebookLike> getAll();

    Mono<SuccessResponse> likePost(FacebookLikeRequest request);

    Mono<SuccessResponse> likeComment(FacebookLikeRequest request);
}
