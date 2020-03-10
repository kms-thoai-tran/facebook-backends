package com.example.facebookbackend.service;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IDynamoDbService {
    void createTable();

    void deleteTable(String table);

    CompletableFuture<Map<String, AttributeValue>> putItem(Map<String, AttributeValue> item);

    CompletableFuture<Map<String, AttributeValue>> updateItem(Map<String, AttributeValue> itemKey, Map<String, AttributeValueUpdate> updatedValue);

    CompletableFuture<Map<String, AttributeValue>> findByItemRequest(GetItemRequest getItemRequest);

    CompletableFuture<List<Map<String, AttributeValue>>> query(QueryRequest queryRequest);
}
