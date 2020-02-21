package com.example.facebookbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post extends DbEntityBase {
    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    UUID id;

    String text;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagId", referencedColumnName = "id")
    Set<User> tags;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    Set<FacebookLike> facebookLikes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId", referencedColumnName = "id")
    Set<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "imageId", referencedColumnName = "id")
    Set<Image> images;
}
