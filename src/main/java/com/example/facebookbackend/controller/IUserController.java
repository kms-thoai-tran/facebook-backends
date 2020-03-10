package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.UserSignUpRequest;
import com.example.facebookbackend.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.List;

@Validated
@RequestMapping(path = "v1/users")
public interface IUserController {

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.OPTIONS})
    ResponseEntity<List<UserResponse>> getAll();

    @PostMapping("/sign-up")
    ResponseEntity<Mono<UserResponse>> signUp(@RequestBody UserSignUpRequest userSignUpRequest);

}
