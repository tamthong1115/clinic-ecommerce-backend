package com.fg.appointment.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "medical_test")
@Data
public class MedicalTest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID medicalTestId;

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

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
