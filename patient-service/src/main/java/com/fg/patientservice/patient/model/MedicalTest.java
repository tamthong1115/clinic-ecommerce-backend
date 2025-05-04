package com.fg.patientservice.patient.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "medical_test")
@Data
public class MedicalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private String medicalTestId;

    @Column(name = "appointment_id", nullable = false)
    private String appointmentId;

    @Column(name = "test_name", nullable = false)
    private String testName;

    @Column(name = "test_result", nullable = false)
    private String testResult;

    @Column(name = "result_file")
    private String resultFile;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
