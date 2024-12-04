package com.example.health_guardian_server.services;

import java.io.File;
import org.springframework.stereotype.Service;

@Service
public interface MinioClientService {
  void storeObject(File file, String fileName, String contentType, String bucketName);

  String getObjectUrl(String objectKey, String bucketName);

  void deleteObject(String objectKey, String bucketName);

  long countObjectsInBucket(String bucketName);
}
