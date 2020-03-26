package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.FacebookLikeRequest;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.List;

@Validated
@RequestMapping(path = "v1/users/like")
public interface IFacebookLikeController {
    @GetMapping()
    ResponseEntity<List<FacebookLike>> getAll();

    @RequestMapping(path = "/sync-up", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    ResponseEntity<SuccessResponse> syncUp();

    @RequestMapping(path = "/comment", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    ResponseEntity<Mono<SuccessResponse>> likeComment(@RequestBody FacebookLikeRequest request);

    @RequestMapping(path = "/post", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    ResponseEntity<Mono<SuccessResponse>> likePost(@RequestBody FacebookLikeRequest request);
}
