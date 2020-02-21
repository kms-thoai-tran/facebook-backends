package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Validated
@RequestMapping(path = "v1/users/likes")
public interface IFacebookLikeController {
    @GetMapping()
    ResponseEntity<List<FacebookLike>> getAll();

    @RequestMapping(path = "/sync-up", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    ResponseEntity<SuccessResponse> syncUp();
}
