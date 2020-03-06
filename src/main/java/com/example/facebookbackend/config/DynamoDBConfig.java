package com.example.facebookbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

import java.net.URI;

@Configuration
public class DynamoDBConfig {
    @Value("${dynamodb.endpoint}")
    String dynamoDBEndpoint;

    @Bean
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        return DynamoDbAsyncClient.builder()
                .endpointOverride(URI.create(dynamoDBEndpoint))
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();
    }
}
