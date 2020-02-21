package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Validated
@RequestMapping(path = "v1/users/post")
public interface IPostController {

    @RequestMapping(path = "/{id}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    ResponseEntity<PostResponse> getPostById(@PathVariable("id") @Valid UUID id);

    @PostMapping()
    ResponseEntity<PostResponse> create(@RequestBody PostRequest postRequest);

    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.OPTIONS})
    ResponseEntity<SuccessResponse> update(@PathVariable("id") @Valid UUID id, @Valid @RequestBody PostRequest postRequest);

    @RequestMapping(path = "/{id}", method = {RequestMethod.DELETE, RequestMethod.OPTIONS})
    ResponseEntity<SuccessResponse> delete(@PathVariable("id") @Valid UUID postId);
}
