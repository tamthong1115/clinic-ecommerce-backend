package com.fg.appointment.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "medical_record")
public class MedicalRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private UUID medicalRecordId;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "doctor_id", nullable = false)
    private String doctorId;

    @Column(name = "clinic_id", nullable = false)
    private String clinicId;

    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @Column(name = "prescription", nullable = false)
    private String prescription;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "updated_at", nullable = false)
    private String updatedAt;

    @OneToOne(mappedBy = "medicalRecord")
    private Appointment appointment;
}