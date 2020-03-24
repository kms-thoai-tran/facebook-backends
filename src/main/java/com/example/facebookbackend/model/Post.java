package com.example.facebookbackend.model;

import com.example.facebookbackend.util.FacebookLikeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends DbEntityBase {
    UUID id;
    String text;
    UUID userId;
    Set<UUID> tagIds;
    Map<String, FacebookLikeType> facebookLikes;
    Map<String, String> comment;
    List<String> images;
}
