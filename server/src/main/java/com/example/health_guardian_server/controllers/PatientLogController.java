package com.example.health_guardian_server.controllers;

import static com.example.health_guardian_server.exceptions.file.FileErrorCode.CAN_NOT_STORE_FILE;
import static com.example.health_guardian_server.utils.Utils.convertMultipartFileToFile;
import static com.example.health_guardian_server.utils.Utils.generateFileName;

import com.example.health_guardian_server.dtos.requests.ListPatientLogRequest;
import com.example.health_guardian_server.dtos.requests.UpdatePatientLogRequest;
import com.example.health_guardian_server.dtos.responses.PatientLogResponse;
import com.example.health_guardian_server.entities.PatientLog;
import com.example.health_guardian_server.exceptions.file.FileException;
import com.example.health_guardian_server.services.MinioClientService;
import com.example.health_guardian_server.services.PatientLogService;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/patient-logs")
@RequiredArgsConstructor
@Slf4j
public class PatientLogController {
  private final PatientLogService patientLogService;
  private final MinioClientService minioClientService;

  @GetMapping("/all")
  public ResponseEntity<Page<PatientLogResponse>> getAllPatientLogs(
      @ModelAttribute ListPatientLogRequest request) {

  @GetMapping
  public ResponseEntity<Page<PatientLogResponse>> getAllPatientLogs(@ModelAttribute ListPatientLogRequest request) {
    Page<PatientLogResponse> patientLogs = patientLogService.getAllPatientLogs(request);

    return new ResponseEntity<>(patientLogs, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PatientLogResponse> getPatientLogById(String id) {
    PatientLogResponse patientLog = patientLogService.getPatientLogById(id);
    return new ResponseEntity<>(patientLog, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PatientLogResponse> createPatientLog(@RequestBody PatientLog patientLog) {
    PatientLogResponse createdPatientLog = patientLogService.createPatientLog(patientLog);
    return new ResponseEntity<>(createdPatientLog, HttpStatus.CREATED);
  }
  @PutMapping("/update/{id}")
  public ResponseEntity<PatientLogResponse> updatePatientLog(
      String id, UpdatePatientLogRequest updatePatientLogRequest) {
    PatientLogResponse updatedPatientLog = patientLogService.updatePatientLog(id, updatePatientLogRequest);
  @PutMapping("/{id}")
  public ResponseEntity<PatientLogResponse> updatePatientLog(String id, PatientLog patientLog) {
    PatientLogResponse updatedPatientLog = patientLogService.updatePatientLog(id, patientLog);
    return new ResponseEntity<>(updatedPatientLog, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePatientLog(String id) {
    patientLogService.deletePatientLog(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PatientLogResponse> getPatientLog(@PathVariable("id") String id) {
    return ResponseEntity.ok(patientLogService.getPatientLogById(id));
  }

  @PostMapping("/upload-images/{id}")
  public ResponseEntity<PatientLogResponse> uploadFiles(
      @RequestPart("patient-log-files") List<MultipartFile> fileList,
      @PathVariable("id") String patientId) {
    List<String> fileNames = new ArrayList<>();
    for (MultipartFile a : fileList) {
      String contentType = a.getContentType();
      String fileName = generateFileName(contentType.split("/")[0], contentType.split("/")[1]);
      try {
        File file = convertMultipartFileToFile(a, fileName);
        minioClientService.storeObject(file, fileName, contentType, "patient-log-files");
        Files.delete(file.toPath());
        fileNames.add(fileName);
        log.info("File uploaded successfully: {}", file.toPath());
      } catch (Exception e) {
        log.error("Error while uploading file: {}", e.getMessage());
        throw new FileException(CAN_NOT_STORE_FILE, HttpStatus.UNPROCESSABLE_ENTITY);
      }
    }
    return ResponseEntity.ok(
        patientLogService.updatePatientLog(
            patientId, UpdatePatientLogRequest.builder().fileNames(fileNames).build()));
  }
}
