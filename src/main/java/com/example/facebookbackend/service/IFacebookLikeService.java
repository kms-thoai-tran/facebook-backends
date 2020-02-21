package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;

import java.util.List;

public interface IFacebookLikeService {
    SuccessResponse syncUp();

    List<FacebookLike> getAll();
}
