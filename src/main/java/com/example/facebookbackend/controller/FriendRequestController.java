package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.FriendRequestRequest;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.dto.response.UserResponse;
import com.example.facebookbackend.repository.IFriendRequestRepository;
import com.example.facebookbackend.service.IFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class FriendRequestController implements IFriendRequestController {

    @Autowired
    IFriendRequestService friendRequestService;


    @Autowired
    IFriendRequestRepository friendRequestRepository;

    @Override
    public ResponseEntity<List<UserResponse>> getFriendRequests(@Valid UUID userId) {
        return null;
    }

    @Override
    public ResponseEntity<SuccessResponse> createFriendRequest(@Valid FriendRequestRequest friendRequestRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(friendRequestService.createFriendRequest(friendRequestRequest));
    }

    @Override
    public ResponseEntity<SuccessResponse> acceptFriendRequest(@Valid FriendRequestRequest friendRequestRequest) {
        return ResponseEntity.ok().body(friendRequestService.acceptFriendRequest(friendRequestRequest));
    }

    @Override
    public ResponseEntity<SuccessResponse> rejectFriendRequest(@Valid FriendRequestRequest friendRequestRequest) {
        return ResponseEntity.ok().body(friendRequestService.rejectFriendRequest(friendRequestRequest));
    }
}
