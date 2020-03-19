package com.example.facebookbackend.service;

import com.example.facebookbackend.model.Post;
import com.example.facebookbackend.model.User;

public interface ITagService {
    void createTag(Post post, User user);
}
