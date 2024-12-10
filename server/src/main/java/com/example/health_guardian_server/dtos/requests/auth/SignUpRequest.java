package com.example.health_guardian_server.dtos.requests.auth;

import com.example.health_guardian_server.entities.enums.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;

@Data
public class SignUpRequest {

  @Email
  @NotBlank
  @Schema(description = "User's email address", example = "user@example.com")
  private String email;

  @NotBlank
  @Size(min = 1, max = 50)
  @Schema(description = "User's first name", example = "John")
  private String firstName;

  @NotBlank
  @Size(min = 1, max = 50)
  @Schema(description = "User's last name", example = "Doe")
  private String lastName;

  @NotBlank
  private Date dateOfBirth;

  @NotBlank
  @NotNull
  private GenderType gender;

  @Size(min = 8)
  @NotBlank
  @Schema(description = "User's password", example = "Password@123")
  private String password;

  @Size(min = 8)
  @NotBlank
  @Schema(description = "User's password", example = "Password@123")
  private String confirmPassword;
}
