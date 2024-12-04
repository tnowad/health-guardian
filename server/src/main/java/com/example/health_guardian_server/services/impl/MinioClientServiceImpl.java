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
    log.info("MinioClientService initialized with endpoint: {}", endpoint);
  }

  @Override
  public void storeObject(File file, String fileName, String contentType, String bucketName) {
    log.info("Storing file '{}' in bucket '{}'", fileName, bucketName);
    try {
      ensureBucketExists(bucketName);
      minioClient.putObject(
        PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
            Files.newInputStream(file.toPath()), file.length(), -1)
          .contentType(contentType)
          .build());
      log.debug("File '{}' stored successfully in bucket '{}'", fileName, bucketName);
    } catch (Exception e) {
      log.error("Error storing file '{}' in bucket '{}'", fileName, bucketName, e);
      throw new FileException(CAN_NOT_STORE_FILE, BAD_REQUEST);
    }
  }

  @Override
  public String getObjectUrl(String objectKey, String bucketName) {
    log.info("Getting URL for object '{}' in bucket '{}'", objectKey, bucketName);
    try {
      String url = minioClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
          .method(Method.GET)
          .bucket(bucketName)
          .object(objectKey)
          .expiry(1, DAYS) // 1 week
          .build());
      log.debug("URL for object '{}' is: {}", objectKey, url);
      return url;
    } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
      log.error("Error getting URL for object '{}' in bucket '{}'", objectKey, bucketName, e);
      throw new FileException(COULD_NOT_READ_FILE, BAD_REQUEST);
    }
  }

  @Override
  public void deleteObject(String objectKey, String bucketName) {
    log.info("Deleting object '{}' from bucket '{}'", objectKey, bucketName);
    try {
      GetObjectResponse response =
        minioClient.getObject(
          GetObjectArgs.builder().bucket(bucketName).object(objectKey).build());

      if (response == null) {
        log.warn("File '{}' not found in bucket '{}'", objectKey, bucketName);
        throw new FileException(FILE_NOT_FOUND, BAD_REQUEST);
      }

      minioClient.removeObject(
        RemoveObjectArgs.builder().bucket(bucketName).object(objectKey).build());
      log.debug("Object '{}' deleted successfully from bucket '{}'", objectKey, bucketName);
    } catch (Exception e) {
      log.error("Error deleting object '{}' from bucket '{}'", objectKey, bucketName, e);
      throw new FileException(CAN_NOT_DELETE_FILE, BAD_REQUEST);
    }
  }

  private void ensureBucketExists(String bucketName) {
    log.info("Ensuring that bucket '{}' exists", bucketName);
    try {
      boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
      if (!found) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        log.info("Bucket '{}' created.", bucketName);
      }
    } catch (Exception e) {
      log.error("Error checking if bucket '{}' exists", bucketName, e);
      throw new FileException(CAN_NOT_CHECK_BUCKET, BAD_REQUEST);
    }
  }

  @Override
  public long countObjectsInBucket(String bucketName) {
    log.info("Counting objects in bucket '{}'", bucketName);
    try {
      Iterable<Result<Item>> results =
        minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
      long count = StreamSupport.stream(results.spliterator(), false).count();
      log.debug("Found {} objects in bucket '{}'", count, bucketName);
      return count;
    } catch (Exception e) {
      log.error("Error counting objects in bucket '{}'", bucketName, e);
      throw new FileException(CAN_NOT_CHECK_BUCKET, BAD_REQUEST);
    }
  }
}
