package com.example.facebookbackend.dto.request;

import com.example.facebookbackend.dto.response.SuccessResponse;

import java.util.UUID;

public class FacebookLikeRequest extends SuccessResponse {
    UUID id;
    UUID userId;

    public FacebookLikeRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
