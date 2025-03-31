package com.fg.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be a valid email address")
  private String email;

  @NotBlank(message = "Password is mandatory")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  private String password;

}
