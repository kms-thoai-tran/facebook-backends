package com.example.facebookbackend.repository;

import com.example.facebookbackend.model.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepositoryImplementation<User, UUID> {
    User findByEmail(String email);

    List<User> findByIdIn(Iterable<UUID> ids);
}
