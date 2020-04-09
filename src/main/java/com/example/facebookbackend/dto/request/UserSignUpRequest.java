package com.example.facebookbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpRequest {
    @NotNull(message = "email is required")
    String email;
    @NotNull(message = "password is required")
    String password;

    @NotNull(message = "name is required")
    String name;
}
