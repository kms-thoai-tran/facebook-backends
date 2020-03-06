package com.example.facebookbackend.model;

import com.example.facebookbackend.util.FacebookLikeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class FacebookLike extends DbEntityBase {
    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    UUID id;

    @Enumerated(EnumType.STRING)
    FacebookLikeType type;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "likeId", referencedColumnName = "id")
//    User user;

    public FacebookLike() {

    }

    ;

    public FacebookLike(FacebookLikeType type) {
        this.type = type;
    }

    ;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FacebookLikeType getType() {
        return type;
    }

    public void setType(FacebookLikeType type) {
        this.type = type;
    }
}
