package com.example.facebookbackend.controller;

import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;
import com.example.facebookbackend.service.IFacebookLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FacebookLikeController implements IFacebookLikeController {
    @Autowired
    IFacebookLikeService facebookLikeService;

    @Override
    public ResponseEntity<List<FacebookLike>> getAll() {
        return ResponseEntity.ok().body(facebookLikeService.getAll());
    }

    @Override
    public ResponseEntity<SuccessResponse> syncUp() {
        return ResponseEntity.ok().body(facebookLikeService.syncUp());
    }
}
