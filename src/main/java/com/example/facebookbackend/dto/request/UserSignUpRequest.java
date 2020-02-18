package com.example.facebookbackend.dto.request;

public class UserSignUpRequest {
    String email;
    String password;

    public UserSignUpRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
