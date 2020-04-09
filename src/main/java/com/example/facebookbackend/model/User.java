package com.example.facebookbackend.model;

import com.example.facebookbackend.dto.request.UserSignUpRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends DbEntityBase {
    UUID id;
    String email;
    String password;
    String role;
    boolean enabled;
    String name;


    public static User fromEntity(UserSignUpRequest userSignUpRequest) {
        if (userSignUpRequest == null) {
            return null;
        }
        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode(userSignUpRequest.getPassword()));
        user.setEmail(userSignUpRequest.getEmail());
        user.setName(userSignUpRequest.getName());
        return user;
    }
}
