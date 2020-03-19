package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.FriendRequestRequest;
import com.example.facebookbackend.dto.response.FriendRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequestMapping(path = "v1/users/friend-requests")
public interface IFriendRequestController {
    @GetMapping()
    ResponseEntity<Mono<List<FriendRequestResponse>>> getFriendRequests(@RequestParam("status") String status);

    @PostMapping("/create")
    ResponseEntity<Mono<FriendRequestResponse>> createFriendRequest(@RequestBody @Valid FriendRequestRequest friendRequestRequest);

    @PostMapping("/accept")
    ResponseEntity<Mono<FriendRequestResponse>> acceptFriendRequest(@RequestBody @Valid FriendRequestRequest friendRequestRequest);

    @PostMapping("/reject")
    ResponseEntity<Mono<FriendRequestResponse>> rejectFriendRequest(@RequestBody @Valid FriendRequestRequest friendRequestRequest);
}
