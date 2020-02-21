package com.example.facebookbackend.repository;

import com.example.facebookbackend.model.FacebookLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IFacebookLikeRepository extends JpaRepository<FacebookLike, UUID> {
    List<FacebookLike> findByIdIn(Iterable<UUID> facebookLikeIds);
}
