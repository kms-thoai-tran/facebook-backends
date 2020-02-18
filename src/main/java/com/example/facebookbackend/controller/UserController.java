package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.UserResponse;
import com.example.facebookbackend.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}
