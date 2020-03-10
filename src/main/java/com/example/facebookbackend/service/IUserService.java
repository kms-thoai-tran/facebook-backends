package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.UserSignUpRequest;
import com.example.facebookbackend.dto.response.UserResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserService {
    List<UserResponse> getAll();

    Mono<UserResponse> signUp(UserSignUpRequest userSignUpRequest);
}
