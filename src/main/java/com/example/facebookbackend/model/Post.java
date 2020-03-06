package com.example.facebookbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends DbEntityBase {
    UUID id;
    String text;
    UUID userId;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "postId", referencedColumnName = "id")
//    Set<FacebookLike> facebookLikes;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "commentId", referencedColumnName = "id")
//    Set<Comment> comments;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "imageId", referencedColumnName = "id")
//    Set<Image> images;
}
