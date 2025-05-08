package com.fg.clinicservice.doctor.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DoctorRequest {
    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Gender is required")
    private String gender;

    private MultipartFile profilePicture;

    @NotNull(message = "Experience years is required")
    @PositiveOrZero(message = "Experience years must be positive or zero")
    private Integer experienceYears;

    @NotBlank(message = "Education is required")
    private String education;
} 