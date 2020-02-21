package com.example.facebookbackend.service;

import com.example.facebookbackend.model.User;
import com.example.facebookbackend.model.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BaseService {

    public BaseService() {
    }

    public User getCurrentUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUser();
    }

    public UUID getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
