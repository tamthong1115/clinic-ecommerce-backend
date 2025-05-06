package com.fg.appointment_service.doctor.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DoctorCertificationDTO {
    private UUID id;
    private String certificationName;
    private String certificationNumber;
    private String specialty;
    private String issuedBy;
    private String status;
    private String certificateImageUrl;
    private LocalDateTime issueDate;
    private LocalDateTime expiryDate;
} 