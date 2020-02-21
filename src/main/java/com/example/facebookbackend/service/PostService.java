package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.PostRequest;
import com.example.facebookbackend.dto.response.PostResponse;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.model.FacebookLike;
import com.example.facebookbackend.model.Post;
import com.example.facebookbackend.model.User;
import com.example.facebookbackend.repository.IFacebookLikeRepository;
import com.example.facebookbackend.repository.IPostRepository;
import com.example.facebookbackend.repository.IUserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService extends BaseService implements IPostService {
    @Autowired
    IPostRepository postRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IFacebookLikeRepository facebookLikeRepository;

    @Override
    public PostResponse create(PostRequest postRequest) {
        Optional<User> user = userRepository.findById(getCurrentUserId());
        validatePostRequest(postRequest);
        Post post = updatePost(new Post(), postRequest);
        post.setCreatedBy(getCurrentUser().getId());
        post.setUser(user.get());
        Post result = postRepository.save(post);
        return PostResponse.convertFrom(result);
    }

    @Override
    public SuccessResponse update(UUID id, PostRequest postRequest) {
        validatePostRequest(postRequest);
        Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isPresent()) {
            Post post = updatePost(postOpt.get(), postRequest);
            post.setModifiedBy(getCurrentUser().getId());
            postRepository.save(post);
            return new SuccessResponse();

        }
        throw new RuntimeException("cannot update the post");
    }

    @Override
    public SuccessResponse delete(UUID postId) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isPresent()) {
            postRepository.delete(postOpt.get());
            return new SuccessResponse();
        }
        throw new RuntimeException("cannot delete the post");
    }

    @Override
    public PostResponse getPostById(UUID id) {
        Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isPresent()) {
            return PostResponse.convertFrom(postOpt.get());
        }
        throw new RuntimeException(String.format("post {0} delete the post", id));

    }

    private void validatePostRequest(PostRequest postRequest) {
    }

    private Post updatePost(Post post, PostRequest postRequest) {
        if (!Strings.isBlank(postRequest.getText())) {
            post.setText(postRequest.getText());
        }
        if (postRequest.getTagIds() != null) {
            List<User> users = userRepository.findByIdIn(postRequest.getTagIds());
            post.setTags(users.stream().collect(Collectors.toSet()));
        }

        if (postRequest.getImageIds() != null) {

        }
        if (postRequest.getFacebookLikeIds() != null) {
            List<FacebookLike> likes = facebookLikeRepository.findByIdIn(postRequest.getFacebookLikeIds());
            post.setFacebookLikes(likes.stream().collect(Collectors.toSet()));
        }
        return post;
    }
}
