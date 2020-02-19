package com.example.facebookbackend.dto.request;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class FriendRequestRequest {
    @NotNull(message = "userId is required")
    UUID userId;

    @NotNull(message = "friendId is required")
    UUID friendId;

    public FriendRequestRequest() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId;
    }
}
