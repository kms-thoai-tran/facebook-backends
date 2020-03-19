package com.example.facebookbackend.service;

import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface IDynamoDbService {

    Boolean doesTableExist(String table);

    void createTable();

    void deleteTable(String table);

    CompletableFuture<Map<String, AttributeValue>> putItem(Map<String, AttributeValue> item);

    CompletableFuture<Map<String, AttributeValue>> updateItem(Map<String, AttributeValue> itemKey, Map<String, AttributeValueUpdate> updatedValue);

    CompletableFuture<Map<String, AttributeValue>> getItem(GetItemRequest getItemRequest);

    CompletableFuture<List<Map<String, AttributeValue>>> query(QueryRequest queryRequest);

    CompletableFuture<List<Map<String, AttributeValue>>> findAllByItemRequest(ScanRequest scanRequest);

    void batchWriteItem(BatchWriteItemRequest batchWriteItemRequest);

    Map<String, List<Map<String, AttributeValue>>> batchGetItem(BatchGetItemRequest batchGetItemRequest);

    Map<String, AttributeValue> deleteItem(DeleteItemRequest deleteItemRequest);

}
