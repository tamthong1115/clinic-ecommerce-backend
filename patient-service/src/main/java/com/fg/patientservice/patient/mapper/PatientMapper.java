package com.fg.patientservice.patient.mapper;

import com.fg.patientservice.patient.dto.PatientCreateDTO;
import com.fg.patientservice.patient.dto.PatientDTO;
import com.fg.patientservice.patient.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public Patient toPatient(PatientCreateDTO patientCreateDTO) {
        return Patient.builder()
                .userId(patientCreateDTO.getUserId())
                .firstName(patientCreateDTO.getFirstName())
                .lastName(patientCreateDTO.getLastName())
                .email(patientCreateDTO.getEmail())
                .dateOfBirth(patientCreateDTO.getDateOfBirth())
                .phone(patientCreateDTO.getPhone())
                .address(patientCreateDTO.getAddress())
                .build();
    }

    public PatientDTO toPatientDTO(Patient patient) {
        return PatientDTO.builder()
                .patientId(patient.getPatientId())
                .userId(patient.getUserId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .dateOfBirth(patient.getDateOfBirth())
                .phone(patient.getPhone())
                .address(patient.getAddress())
                .build();
    }
}
