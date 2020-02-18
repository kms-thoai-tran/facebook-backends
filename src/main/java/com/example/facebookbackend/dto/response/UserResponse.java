package com.example.facebookbackend.dto.response;

import com.example.facebookbackend.model.User;

import java.util.UUID;

public class UserResponse extends SuccessResponse {
    public UserResponse() {
    }

    public UserResponse(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    UUID id;
    String email;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserResponse fromEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getEmail());
    }
}
