package com.fg.clinicservice.doctor.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DoctorRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String profilePicture;

    @NotNull(message = "Experience years is required")
    @PositiveOrZero(message = "Experience years must be positive or zero")
    private Integer experienceYears;

    @NotBlank(message = "Education is required")
    private String education;
}