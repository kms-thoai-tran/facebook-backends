package com.example.facebookbackend.repository;

import com.example.facebookbackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPostRepository extends JpaRepository<Post, UUID> {
}
