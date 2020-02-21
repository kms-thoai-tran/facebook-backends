package com.example.facebookbackend.dto.request;


import java.util.Set;
import java.util.UUID;

public class PostRequest {
    String text;
    Set<UUID> tagIds;
    Set<String> imageIds;
    Set<UUID> facebookLikeIds;

    public PostRequest(String text, Set<UUID> tagIds, Set<String> imageIds, Set<UUID> facebookLikeIds) {
        this.text = text;
        this.tagIds = tagIds;
        this.imageIds = imageIds;
        this.facebookLikeIds = facebookLikeIds;
    }

    public PostRequest() {
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

    public Set<UUID> getFacebookLikeIds() {
        return facebookLikeIds;
    }

    public void setFacebookLikeIds(Set<UUID> facebookLikeIds) {
        this.facebookLikeIds = facebookLikeIds;
    }
}
