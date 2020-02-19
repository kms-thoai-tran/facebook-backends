package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.FriendRequestRequest;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@RequestMapping(path = "v1/users/friend-requests")
public interface IFriendRequestController {
    @RequestMapping(path = "/{id}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    ResponseEntity<List<UserResponse>> getFriendRequests(@PathVariable("id") @Valid UUID userId);

    @PostMapping("/create")
    ResponseEntity<SuccessResponse> createFriendRequest(@RequestBody @Valid FriendRequestRequest friendRequestRequest);

    @PostMapping("/accept")
    ResponseEntity<SuccessResponse> acceptFriendRequest(@RequestBody @Valid FriendRequestRequest friendRequestRequest);

    @PostMapping("/reject")
    ResponseEntity<SuccessResponse> rejectFriendRequest(@RequestBody @Valid FriendRequestRequest friendRequestRequest);
}
