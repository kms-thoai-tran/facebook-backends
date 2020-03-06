package com.example.facebookbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class DynamoService implements IDynamoDbService {
    @Value("${dynamodb.table.facebook}")
    String tableName;

    @Autowired
    DynamoDbAsyncClient dynamoDbAsyncClient;

    @Override
    public void createTable() {
        CreateTableRequest createTableRequest = CreateTableRequest.builder()
                .tableName(tableName)
                .attributeDefinitions(AttributeDefinition.builder()
                                .attributeName("PK")
                                .attributeType(ScalarAttributeType.S)
                                .build(),
                        AttributeDefinition.builder()
                                .attributeName("SK")
                                .attributeType(ScalarAttributeType.S)
                                .build())
                .keySchema(KeySchemaElement.builder()
                                .attributeName("PK")
                                .keyType(KeyType.HASH)
                                .build(),
                        KeySchemaElement.builder()
                                .attributeName("SK")
                                .keyType(KeyType.RANGE)
                                .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits((long) 10)
                        .writeCapacityUnits((long) 10).build())
                .build();

        System.out.println("Creating table " + tableName + "...");
        CompletableFuture<CreateTableResponse> response = dynamoDbAsyncClient.createTable(createTableRequest);
        System.out.println("Waiting for " + tableName + " to become ACTIVE...");
        System.out.println("Done");
    }

    @Override
    public void deleteTable(String table) {
        DeleteTableRequest deleteTableRequest = DeleteTableRequest.builder()
                .tableName(table)
                .build();
        dynamoDbAsyncClient.deleteTable(deleteTableRequest);
        System.out.println(tableName + " was successfully deleted!");
    }

    @Override
    public CompletableFuture<Map<String, AttributeValue>> putItem(Map<String, AttributeValue> item) {
        if (item.size() == 0) {
            return null;
        }
        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        CompletableFuture<PutItemResponse> completableFuture = dynamoDbAsyncClient.putItem(putItemRequest);
        return completableFuture.thenApplyAsync(PutItemResponse::attributes);
    }

    @Override
    public CompletableFuture<Map<String, AttributeValue>> updateItem(Map<String, AttributeValue> itemKey, Map<String, AttributeValueUpdate> updatedValue) {
        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(itemKey)
                .attributeUpdates(updatedValue)
                .returnValues(ReturnValue.ALL_NEW)
                .build();
        CompletableFuture<UpdateItemResponse> completableFuture = dynamoDbAsyncClient.updateItem(updateItemRequest);
        return completableFuture.thenApplyAsync(UpdateItemResponse::attributes);
    }

    @Override
    public CompletableFuture<Map<String, AttributeValue>> findByItemRequest(GetItemRequest getItemRequest) {
        CompletableFuture<GetItemResponse> getItemResponseCompletableFuture = dynamoDbAsyncClient.getItem(getItemRequest);

        return getItemResponseCompletableFuture.thenApply(GetItemResponse::item);
    }
}
