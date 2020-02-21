package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;
import com.example.facebookbackend.repository.IFacebookLikeRepository;
import com.example.facebookbackend.util.FacebookLikeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacebookLikeService implements IFacebookLikeService {

    @Autowired
    IFacebookLikeRepository facebookLikeRepository;

    @Override
    public SuccessResponse syncUp() {
        List<FacebookLike> likes = new ArrayList<>();
        likes.add(new FacebookLike(FacebookLikeType.Like));
        likes.add(new FacebookLike(FacebookLikeType.Love));
        likes.add(new FacebookLike(FacebookLikeType.Haha));
        likes.add(new FacebookLike(FacebookLikeType.Wow));
        likes.add(new FacebookLike(FacebookLikeType.Angry));
        facebookLikeRepository.saveAll(likes);
        return new SuccessResponse();
    }

    @Override
    public List<FacebookLike> getAll() {
        return facebookLikeRepository.findAll();
    }
}
