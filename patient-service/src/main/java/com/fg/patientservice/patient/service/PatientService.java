package com.fg.patientservice.patient.service;



import com.fg.patientservice.patient.dto.PatientCreateDTO;
import com.fg.patientservice.patient.dto.PatientDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientService {
    PatientDTO createPatient(PatientCreateDTO patientCreateDTO);
    Optional<PatientDTO> getPatientById(UUID id);
    Optional<PatientDTO> getPatientByUserId(UUID userId);
    Optional<PatientDTO> getPatientByEmail(String email);
    List<PatientDTO> getAllPatients();
    PatientDTO updatePatient(UUID id, PatientCreateDTO patientCreateDTO);
    void deletePatient(UUID id);
    boolean existsByUserId(UUID userId);
    boolean existsByEmail(String email);
}