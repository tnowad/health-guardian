package com.example.health_guardian_server.services.impl;

import static com.example.health_guardian_server.exceptions.file.FileErrorCode.*;
import static java.util.concurrent.TimeUnit.DAYS;
import static org.springframework.http.HttpStatus.*;

import com.example.health_guardian_server.dtos.events.HandleFileEvent;
import com.example.health_guardian_server.exceptions.file.FileException;
import com.example.health_guardian_server.services.MinioClientService;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.Item;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.stream.StreamSupport;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MinioClientServiceImpl implements MinioClientService {
  MinioClient minioClient;

  @Value("${minio.bucket-name}")
  @NonFinal
  String bucketName;

  @Value("${minio.temp-bucket-name}")
  @NonFinal
  String tempBucketName;

  public MinioClientServiceImpl(
      @Value("${minio.endpoint}") String endpoint,
      @Value("${minio.access-key}") String accessKey,
      @Value("${minio.secret-key}") String secretKey,
      @Autowired KafkaTemplate<String, HandleFileEvent> fileStorageTemplate) {
    this.minioClient =
        MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
  }

  @Override
  public void storeObject(File file, String fileName, String contentType, String bucketName) {
    try {
      ensureBucketExists(bucketName);
      minioClient.putObject(
          PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                  Files.newInputStream(file.toPath()), file.length(), -1)
              .contentType(contentType)
              .build());

    } catch (Exception e) {
      throw new FileException(CAN_NOT_STORE_FILE, BAD_REQUEST);
    }
  }

  @Override
  public String getObjectUrl(String objectKey, String bucketName) {
    try {
      return minioClient.getPresignedObjectUrl(
          GetPresignedObjectUrlArgs.builder()
              .method(Method.GET)
              .bucket(bucketName)
              .object(objectKey)
              .expiry(1, DAYS) // 1 week
              .build());

    } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
      log.error("Error getting object URL", e);
      throw new FileException(COULD_NOT_READ_FILE, BAD_REQUEST);
    }
  }

  @Override
  public void deleteObject(String objectKey, String bucketName) {
    GetObjectResponse response;
    try {
      response =
          minioClient.getObject(
              GetObjectArgs.builder().bucket(bucketName).object(objectKey).build());
    } catch (Exception e) {
      throw new FileException(COULD_NOT_READ_FILE, BAD_REQUEST);
    }

    if (response == null) throw new FileException(FILE_NOT_FOUND, BAD_REQUEST);

    try {
      minioClient.removeObject(
          RemoveObjectArgs.builder().bucket(bucketName).object(objectKey).build());

    } catch (Exception e) {
      throw new FileException(CAN_NOT_DELETE_FILE, BAD_REQUEST);
    }
  }

  private void ensureBucketExists(String bucketName) {
    boolean found;
    try {
      found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
      if (!found) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        log.info("Bucket '{}' created.", bucketName);
      }
    } catch (Exception e) {
      throw new FileException(CAN_NOT_CHECK_BUCKET, BAD_REQUEST);
    }
  }

  @Override
  public long countObjectsInBucket(String bucketName) {
    try {
      Iterable<Result<Item>> results =
          minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
      return StreamSupport.stream(results.spliterator(), false).count();
    } catch (Exception e) {
      log.error("Error counting objects in bucket", e);
      throw new FileException(CAN_NOT_CHECK_BUCKET, BAD_REQUEST);
    }
  }
}
