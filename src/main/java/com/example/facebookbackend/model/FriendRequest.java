package com.example.facebookbackend.model;

import com.example.facebookbackend.util.FriendRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequest {
    UUID friendId;
    FriendRequestStatus status;
}
