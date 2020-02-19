package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.FriendRequestRequest;
import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.dto.response.UserResponse;
import com.example.facebookbackend.model.FriendRequest;
import com.example.facebookbackend.model.User;
import com.example.facebookbackend.repository.IFriendRequestRepository;
import com.example.facebookbackend.repository.IUserRepository;
import com.example.facebookbackend.util.FriendRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FriendRequestService implements IFriendRequestService {
    @Autowired
    IFriendRequestRepository friendRequestRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public List<UserResponse> getFriendRequests(UUID userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return null;
    }

    @Override
    public SuccessResponse createFriendRequest(FriendRequestRequest friendRequestRequest) {
        List<User> users = userRepository.findByIdIn(Arrays.asList(friendRequestRequest.getUserId(), friendRequestRequest.getFriendId()));
        if (users.size() == 2) {
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setUserId(friendRequestRequest.getUserId());
            friendRequest.setFriendId(friendRequestRequest.getFriendId());
            friendRequest.setStatus(FriendRequestStatus.NEW);
            friendRequestRepository.save(friendRequest);
            return new SuccessResponse();
        }
        throw new RuntimeException("Cannot create friend request");
    }

    @Override
    @Transactional
    public SuccessResponse acceptFriendRequest(FriendRequestRequest friendRequestRequest) {
        List<User> users = userRepository.findByIdIn(Arrays.asList(friendRequestRequest.getUserId(), friendRequestRequest.getFriendId()));
        Optional<User> userOpt = users.stream().filter(x -> x.getId().equals(friendRequestRequest.getUserId())).findFirst();
        Optional<User> friendOpt = users.stream().filter(x -> x.getId().equals(friendRequestRequest.getFriendId())).findFirst();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.getUserFriends().add(friendOpt.get());
            userRepository.save(user);
            FriendRequest friendRequest = friendRequestRepository.findByUserIdAndFriendId(friendRequestRequest.getUserId(), friendRequestRequest.getFriendId());
            friendRequestRepository.delete(friendRequest);
            return new SuccessResponse();
        }
        throw new RuntimeException("Cannot accept friend request");
    }

    @Override
    public SuccessResponse rejectFriendRequest(FriendRequestRequest friendRequestRequest) {
        FriendRequest friendRequest = friendRequestRepository.findByUserIdAndFriendId(friendRequestRequest.getUserId(), friendRequestRequest.getFriendId());
        if (friendRequest != null) {
            friendRequest.setStatus(FriendRequestStatus.REJECT);
            friendRequestRepository.save(friendRequest);
            return new SuccessResponse();
        }
        throw new RuntimeException("Cannot reject friend request");
    }

}
