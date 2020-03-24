package com.example.facebookbackend.service.s3;

import com.example.facebookbackend.dto.response.SuccessResponse;
import com.example.facebookbackend.dto.response.UploadResponse;
import com.example.facebookbackend.exception.UploadFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkResponse;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3AsyncClientBuilder;
import software.amazon.awssdk.services.s3.model.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class AmazonS3Service implements IAmazonS3Service {
    private S3AsyncClient amazonS3Client;

    @Value("${app.awsServices.bucketName}")
    private String bucketName;
    private String accessKey = "123";
    private String secretKey = "456";

    @Value("${app.awsServices.endpointUrl}")
    private String endpointUrl;

    @Override
    public SuccessResponse uploadMultipleFiles(List<MultipartFile> files) {
        Set<String> fileNameUpdated = new HashSet<>();
        if (files != null) {
            files.forEach(multipartFile -> {
                File file = convertMultiPartFileToFile(multipartFile);
                String uniqueFileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
                String result = uploadFileToS3bucket(uniqueFileName, file, bucketName);
                fileNameUpdated.add(result);
            });
        }

        return UploadResponse.builder().keys(fileNameUpdated).build();
    }

    @Override
    public void createBucket(String bucket) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucket)
                .createBucketConfiguration(CreateBucketConfiguration.builder()
                        .locationConstraint(Region.US_WEST_2.id())
                        .build())
                .acl(BucketCannedACL.PUBLIC_READ)
                .build();
        amazonS3Client.createBucket(createBucketRequest);
    }

    @Override
    public void deleteBucket(String bucketName) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder().bucket(bucketName).build();
        CompletableFuture<ListObjectsV2Response> listObjectsV2Response;
        do {
            listObjectsV2Response = amazonS3Client.listObjectsV2(listObjectsV2Request);
            listObjectsV2Response.join().contents().stream().forEach(o -> {
                amazonS3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(o.key()).build());
            });
            listObjectsV2Request = ListObjectsV2Request.builder().bucket(bucketName)
                    .continuationToken(listObjectsV2Response.join().nextContinuationToken()).build();
        } while (listObjectsV2Response.join().isTruncated());

        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
        amazonS3Client.deleteBucket(deleteBucketRequest);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

//    private void deleteFileFromS3bucket(String fileName, String bucketName) {
//        amazonS3Client.deleteObject(bucketName, fileName);
//    }

    //    private S3Object downloadFileFromS3bucket(String fileName, String bucketName) {
//        S3Object object = amazonS3Client.getObject(bucketName, fileName);
//        return object;
//
//    }
//
    private String uploadFileToS3bucket(String fileName, File file, String bucketName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .contentType(MediaType.IMAGE_JPEG_VALUE)
                .build();
        CompletableFuture<PutObjectResponse> responseCompletableFuture = amazonS3Client.putObject(putObjectRequest, AsyncRequestBody.fromFile(file));
        checkResult(responseCompletableFuture.join());
        return fileName;
    }

    /**
     * check result from an API call.
     *
     * @param result Result from an API call
     */
    private static void checkResult(SdkResponse result) {
        if (result.sdkHttpResponse() == null || !result.sdkHttpResponse().isSuccessful()) {
            throw new UploadFailedException(result);
        }
    }


    @PostConstruct
    private void initializeAmazon() {
//        AwsCredentials credentials = AwsBasicCredentials.create(this.accessKey, this.secretKey);
//        StaticCredentialsProvider credProvider = StaticCredentialsProvider
//                .create(credentials);
//        // mock localstack
//        clientCfg.setProtocol(Protocol.HTTP);
//        clientCfg.setProxyHost(endpointUrl);
//        clientCfg.setProxyPort(4572);
//        amazonS3Client = new AmazonS3Client(credentials, clientCfg);
        // real service
//        s3client = AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUrl,"us-east-1"))
        S3AsyncClientBuilder builder = S3AsyncClient.builder();
        builder.endpointOverride(URI.create(endpointUrl));
        builder.region(Region.US_WEST_2);
        amazonS3Client = builder.build();

    }
}
