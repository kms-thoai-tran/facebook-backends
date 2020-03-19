package com.example.facebookbackend.service;

import com.example.facebookbackend.dto.request.UserSignUpRequest;
import com.example.facebookbackend.dto.response.UserResponse;
import com.example.facebookbackend.model.User;
import com.example.facebookbackend.model.UserPrincipal;
import com.example.facebookbackend.repository.IUserRepository;
import com.example.facebookbackend.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class UserService implements UserDetailsService, IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IDynamoDbService dynamoDbService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        HashMap<String, AttributeValue> attrValues =
                new HashMap<String, AttributeValue>();
        attrValues.put(":SK", AttributeValue.builder().s(email).build());
        //set up an alias for the partition key name in case it's a reserved word
//        HashMap<String,String> attrNameAlias = new HashMap<>();

//        attrNameAlias.put("#SKAlias", "SK");


        QueryRequest queryRequest = QueryRequest.builder()
                .tableName("facebook")
                .indexName("SKIndex")
                .keyConditionExpression("SK=:SK")
//                .expressionAttributeNames(attrNameAlias)
                .expressionAttributeValues(attrValues)
                .build();
        CompletableFuture<List<Map<String, AttributeValue>>> query = dynamoDbService.query(queryRequest);
        Optional<User> userOpt = query.join().stream().map(UserMapper::convertByUserPrincipal).findFirst();
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new UserPrincipal(user.getEmail(), user.getPassword(), getGrantedAuthorities(user.getRole()), user);
        }
        throw new RuntimeException("User cannot found ");
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
    public Mono<UserResponse> signUp(UserSignUpRequest userSignUpRequest) {
        CompletableFuture<Map<String, AttributeValue>> a = dynamoDbService.putItem(UserMapper.toMapGet(User.builder()
                .id(UUID.randomUUID())
                .email(userSignUpRequest.getEmail())
                .password(userSignUpRequest.getPassword())
                .enabled(true)
                .build()));
        return Mono.fromCompletionStage(a).map(UserMapper::fromMap);
    }
}
