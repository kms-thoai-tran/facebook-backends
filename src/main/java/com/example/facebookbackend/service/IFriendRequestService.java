package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.FriendRequestRequest;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface IFriendRequestService {
    List<UserResponse> getFriendRequests(UUID userId);

    SuccessResponse createFriendRequest(FriendRequestRequest friendRequestRequest);

    SuccessResponse acceptFriendRequest(FriendRequestRequest friendRequestRequest);

    SuccessResponse rejectFriendRequest(FriendRequestRequest friendRequestRequest);
}
