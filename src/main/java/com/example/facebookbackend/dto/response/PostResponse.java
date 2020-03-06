package com.example.facebookbackend.dto.response;

import com.example.facebookbackend.model.Post;

import java.util.Set;
import java.util.UUID;

public class PostResponse extends SuccessResponse {
    UUID id;
    String text;
    Set<UUID> tagIds;
    Set<String> imageIds;
    Set<UUID> likes;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<UUID> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<UUID> tagIds) {
        this.tagIds = tagIds;
    }

    public Set<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(Set<String> imageIds) {
        this.imageIds = imageIds;
    }

    public Set<UUID> getLikes() {
        return likes;
    }

    public void setLikes(Set<UUID> likes) {
        this.likes = likes;
    }

    public static PostResponse convertFrom(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
//        if (post.getTags() != null) {
//            postResponse.setTagIds(post.getTags().stream().map(x -> x.getId()).collect(Collectors.toSet()));
//        }
        if (post.getText() != null) {
            postResponse.setText(post.getText());
        }
//        if (post.getFacebookLikes() != null) {
//            postResponse.setLikes(post.getFacebookLikes().stream().map(x -> x.getId()).collect(Collectors.toSet()));
//        }
//        if (post.getImages() != null) {
//            postResponse.setImageIds(post.getImages().stream().map(x -> x.getUrl()).collect(Collectors.toSet()));
//        }
        return postResponse;
    }
}
