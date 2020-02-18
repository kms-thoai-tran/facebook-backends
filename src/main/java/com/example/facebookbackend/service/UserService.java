package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.UserSignUpRequest;
import com.example.facebookbackend.dto.response.UserResponse;
import com.example.facebookbackend.model.User;
import com.example.facebookbackend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class UserService implements UserDetailsService, IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
//       user.setPassword("$2a$10$wFz8eHQejQDU/2L8JxP75eECkTlYz/BCvDfKv/fqFQvlhpkD3Hc7W");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(String role) {
        return asList(() -> "ADMIN");
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            return new ArrayList<>();
        }
        return users.stream().map(UserResponse::fromEntity).collect(Collectors.toList());
    }

    @Override
    public UserResponse signUp(UserSignUpRequest userSignUpRequest) {
        User user = userRepository.save(User.fromEntity(userSignUpRequest));
        return UserResponse.fromEntity(user);
    }
}
