package com.fg.appointment_service.doctor.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class DoctorCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    String certificationName;
    String certificationNumber;
    String specialty;
    String issuedBy;
    String status;
    String certificateImageUrl;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime issueDate;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime expiryDate;
}
