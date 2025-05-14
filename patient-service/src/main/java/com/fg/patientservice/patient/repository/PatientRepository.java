package com.fg.patientservice.patient.repository;

import com.fg.patientservice.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByUserId(UUID userId);
    boolean existsByUserId(UUID userId);
    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
}
