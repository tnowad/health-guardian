package com.example.health_guardian_server.utils;

import static com.example.health_guardian_server.utils.Constants.ALLOWED_MEDIA_TYPES;

import com.example.health_guardian_server.exceptions.file.FileErrorCode;
import com.example.health_guardian_server.exceptions.file.FileException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public class Utils {

  public static boolean isMedia(String contentType) {
    return contentType != null && ALLOWED_MEDIA_TYPES.contains(contentType);
  }

  public static File convertMultipartFileToFile(MultipartFile multipartFile, String fileName)
      throws IOException {
    File file = new File(fileName);
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(multipartFile.getBytes());
    }
    return file;
  }

  public static String generateFileName(String fileType, String extension) {
    // Lấy thời gian hiện tại với format yyyyMMdd_HHmmss
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    String timestamp = LocalDateTime.now().format(dateTimeFormatter);

    // Tạo UUID duy nhất
    String uniqueId = UUID.randomUUID().toString();

    // Kết hợp tên file
    return String.format("%s_%s_%s.%s", fileType, timestamp, uniqueId, extension);
  }

  public static <T extends Enum<?>> T getRandomEnum(Class<T> enumClass) {
    Random random = new Random();
    int x = random.nextInt(enumClass.getEnumConstants().length);
    return enumClass.getEnumConstants()[x];
  }

  public static String getUserIdFromContext() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public static String getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null
        || !authentication.isAuthenticated()
        || authentication.getPrincipal().equals("anonymousUser")) {
      return "anonymous";
    }

    return authentication.getName();
  }

  public static File getRandomFile(String folderPath) {
    int random = new Random().nextInt(9) + 1;
    ClassPathResource resource =
        new ClassPathResource(String.format("%s/%s-%s.jpg", folderPath, folderPath, random));
    log.info("Random file: {}", String.format("%s/%s-%s", folderPath, folderPath, random));
    try {
      return resource.getFile();
    } catch (IOException e) {
      log.error("Error when get random file", e);
      throw new FileException(FileErrorCode.FILE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
  }

  public static Date randomDate(Date start, Date end) {
    long startMillis = start.getTime();
    long endMillis = end.getTime();

    long randomMillis = ThreadLocalRandom.current().nextLong(startMillis, endMillis);
    return new Date(randomMillis);
  }

  public static String toHmacSHA256(String data, String secretKey) throws Exception {

    // Generate HmacSHA256 signature
    Mac hmacSha256 = Mac.getInstance("HmacSHA256");
    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
    hmacSha256.init(secretKeySpec);
    byte[] hash = hmacSha256.doFinal(data.getBytes());
    return Base64.getEncoder().encodeToString(hash);
  }
}
