package com.example.facebookbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class DynamoService implements IDynamoDbService {
    @Value("${dynamodb.table.facebook}")
    String tableName;

    @Autowired
    DynamoDbAsyncClient dynamoDbAsyncClient;

    @Override
    public Boolean doesTableExist(String tableName) {
        CompletableFuture<ListTablesResponse> listTablesResponseCompletableFuture = dynamoDbAsyncClient.listTables();
        return listTablesResponseCompletableFuture.join().tableNames().stream()
                .anyMatch(t -> tableName.compareTo(t) == 0);
    }

    @Override
    public void createTable() {

        ProvisionedThroughput ptIndex = ProvisionedThroughput.builder()
                .writeCapacityUnits(1L)
                .readCapacityUnits(1L).build();

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
                .globalSecondaryIndexes(GlobalSecondaryIndex.builder()
                        .indexName("SKIndex")
                        .provisionedThroughput(ptIndex)
                        .keySchema(KeySchemaElement.builder()
                                .attributeName("SK")
                                .keyType(KeyType.HASH).build()
                        )
                        .projection(Projection.builder().projectionType(ProjectionType.ALL).build())
                        .build())
                .provisionedThroughput(ptIndex)

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
                .returnValues(ReturnValue.ALL_OLD)
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
        return updateItem(updateItemRequest);
    }

    @Override
    public CompletableFuture<Map<String, AttributeValue>> updateItem(UpdateItemRequest request) {
        CompletableFuture<UpdateItemResponse> completableFuture = dynamoDbAsyncClient.updateItem(request);
        return completableFuture.thenApplyAsync(UpdateItemResponse::attributes);
    }

    @Override
    public CompletableFuture<Map<String, AttributeValue>> getItem(GetItemRequest getItemRequest) {
        CompletableFuture<GetItemResponse> getItemResponseCompletableFuture = dynamoDbAsyncClient.getItem(getItemRequest);

        return getItemResponseCompletableFuture.thenApply(GetItemResponse::item);
    }

    @Override
    public CompletableFuture<List<Map<String, AttributeValue>>> query(QueryRequest queryRequest) {
        CompletableFuture<QueryResponse> responseCompletableFuture = dynamoDbAsyncClient.query(queryRequest);
        return responseCompletableFuture.thenApplyAsync(QueryResponse::items);
    }

    @Override
    public CompletableFuture<List<Map<String, AttributeValue>>> scan(ScanRequest scanRequest) {
        CompletableFuture<ScanResponse> scanResponseCompletableFuture = dynamoDbAsyncClient.scan(scanRequest);
        return scanResponseCompletableFuture.thenApplyAsync(ScanResponse::items);
    }

    @Override
    public void batchWriteItem(BatchWriteItemRequest batchWriteItemRequest) {
        CompletableFuture<BatchWriteItemResponse> batchWriteItemResponseCompletableFuture = dynamoDbAsyncClient.batchWriteItem(batchWriteItemRequest);
        CompletableFuture<Map<String, List<WriteRequest>>> result = batchWriteItemResponseCompletableFuture.thenApplyAsync(BatchWriteItemResponse::unprocessedItems);
        if (result.join().size() == 0) {
            System.out.println("Update Successful");
        }
    }

    @Override
    public Map<String, List<Map<String, AttributeValue>>> batchGetItem(BatchGetItemRequest batchGetItemRequest) {
        CompletableFuture<BatchGetItemResponse> batchGetItemResponseCompletableFuture = dynamoDbAsyncClient.batchGetItem(batchGetItemRequest);
        return batchGetItemResponseCompletableFuture.join().responses();
    }

    @Override
    public Map<String, AttributeValue> deleteItem(DeleteItemRequest deleteItemRequest) {
        CompletableFuture<DeleteItemResponse> deleteItemResponseCompletableFuture = dynamoDbAsyncClient.deleteItem(deleteItemRequest);
        return deleteItemResponseCompletableFuture.join().attributes();
    }
}
