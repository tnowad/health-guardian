package com.example.health_guardian_server.controllers;

import static com.example.health_guardian_server.exceptions.file.FileErrorCode.CAN_NOT_STORE_FILE;
import static com.example.health_guardian_server.utils.Utils.convertMultipartFileToFile;
import static com.example.health_guardian_server.utils.Utils.generateFileName;

import com.example.health_guardian_server.dtos.responses.FileUploadResponse;
import com.example.health_guardian_server.exceptions.file.FileException;
import com.example.health_guardian_server.services.MinioClientService;
import java.io.File;
import java.nio.file.Files;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileController {

  private final MinioClientService minioClientService;

  @PostMapping("/upload")
  public ResponseEntity<FileUploadResponse> uploadAvatar(
      @RequestPart("avatar") MultipartFile avatar) {
    String contentType = avatar.getContentType();
    String fileName = generateFileName(contentType.split("/")[0], contentType.split("/")[1]);
    try {
      File file = convertMultipartFileToFile(avatar, fileName);
      minioClientService.storeObject(file, fileName, contentType, "files");
      Files.delete(file.toPath());
      log.info("File uploaded successfully: {}", file.toPath());
    } catch (Exception e) {
      throw new FileException(CAN_NOT_STORE_FILE, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    var url = minioClientService.getObjectUrl(fileName, "files");
    return ResponseEntity.ok(FileUploadResponse.builder().id(fileName).url(url).build());
  }
}
