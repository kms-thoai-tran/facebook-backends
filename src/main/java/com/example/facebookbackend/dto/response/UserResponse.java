package com.example.facebookbackend.dto.response;

import com.example.facebookbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse extends SuccessResponse {

    String name;
    UUID id;
    String email;

    public UserResponse(UUID id, String email, String name) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public static UserResponse fromEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponse(user.getId(), user.getEmail(), user.getName());
    }
}
