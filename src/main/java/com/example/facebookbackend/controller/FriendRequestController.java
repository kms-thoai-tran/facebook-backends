package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.FriendRequestRequest;
import com.example.facebookbackend.dto.response.FriendRequestResponse;
import com.example.facebookbackend.service.IFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FriendRequestController implements IFriendRequestController {

    @Autowired
    IFriendRequestService friendRequestService;

    @Override
    public ResponseEntity<Mono<List<FriendRequestResponse>>> getFriendRequests(String status) {
        return ResponseEntity.ok().body(friendRequestService.getFriendRequests(status));
    }

    @Override
    public ResponseEntity<Mono<FriendRequestResponse>> createFriendRequest(@Valid FriendRequestRequest friendRequestRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(friendRequestService.createFriendRequest(friendRequestRequest));
    }

    @Override
    public ResponseEntity<Mono<FriendRequestResponse>> acceptFriendRequest(@Valid FriendRequestRequest friendRequestRequest) {
        return ResponseEntity.ok().body(friendRequestService.acceptFriendRequest(friendRequestRequest));
    }

    @Override
    public ResponseEntity<Mono<FriendRequestResponse>> rejectFriendRequest(@Valid FriendRequestRequest friendRequestRequest) {
        return ResponseEntity.ok().body(friendRequestService.rejectFriendRequest(friendRequestRequest));
    }
}
