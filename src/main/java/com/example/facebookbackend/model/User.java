package com.example.facebookbackend.model;

import com.example.facebookbackend.dto.request.UserSignUpRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    UUID id;

    @NotBlank(message = "email is required")
    @Column(unique = true)
    String email;

    String password;

    @Column(name = "role", length = 50)
    String role;

    @Column(name = "enabled")
    short enabled;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendId", referencedColumnName = "id")
    Set<User> userFriends;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "friendRequestId", referencedColumnName = "id")
//    @Where(clause = "status = New")
//    Set<FriendRequest> friendRequests;

    public static User fromEntity(UserSignUpRequest userSignUpRequest) {
        if (userSignUpRequest == null) {
            return null;
        }
        User user = new User();
        user.setPassword(new BCryptPasswordEncoder().encode(userSignUpRequest.getPassword()));
        user.setEmail(userSignUpRequest.getEmail());
        return user;
    }
}
