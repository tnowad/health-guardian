
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
@RequestMapping("/files")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FileController {

  private final MinioClientService minioClientService;

  @PostMapping("/upload")
  public ResponseEntity<FileUploadResponse> uploadAvatar(
      @RequestPart("file") MultipartFile avatar) {
    String contentType = avatar.getContentType();
    String fileName = generateFileName(contentType.split("/")[0], contentType.split("/")[1]);
    try {
      log.info("Starting file conversion for: {}", avatar.getOriginalFilename());
      File file = convertMultipartFileToFile(avatar, fileName);
      log.info("File converted to local File object: {}", file.getPath());

      minioClientService.storeObject(file, fileName, contentType, "files");
      log.info("File stored in Minio successfully with name: {}", fileName);

      Files.delete(file.toPath());
      log.info("Local file deleted after successful upload: {}", file.getPath());
    } catch (Exception e) {
      log.error("Failed to store file: {} - Error: {}", avatar.getOriginalFilename(), e.getMessage());
      throw new FileException(CAN_NOT_STORE_FILE, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    var url = minioClientService.getObjectUrl(fileName, "files");
    log.info("Generated URL for the uploaded file: {}", url);

    return ResponseEntity.ok(FileUploadResponse.builder().id(fileName).url(url).build());
  }
}
