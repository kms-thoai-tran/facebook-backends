package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.FriendRequestRequest;
import com.example.facebookbackend.dto.response.FriendRequestResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IFriendRequestService {
    Mono<List<FriendRequestResponse>> getFriendRequests(String status);

    Mono<FriendRequestResponse> createFriendRequest(FriendRequestRequest friendRequestRequest);

    Mono<FriendRequestResponse> acceptFriendRequest(FriendRequestRequest friendRequestRequest);

    Mono<FriendRequestResponse> rejectFriendRequest(FriendRequestRequest friendRequestRequest);
}
