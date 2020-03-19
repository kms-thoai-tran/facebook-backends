package com.example.facebookbackend.dto.response;

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
public class FriendRequestResponse extends SuccessResponse {
    UUID friendId;
    FriendRequestStatus status;
}
