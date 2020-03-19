package com.example.facebookbackend.dto.request;

import com.example.facebookbackend.util.FriendRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequestRequest {
    @NotNull(message = "friendId is required")
    UUID friendId;
    FriendRequestStatus status;
}
