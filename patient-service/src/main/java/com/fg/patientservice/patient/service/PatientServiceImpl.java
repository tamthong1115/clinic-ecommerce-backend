package com.fg.patientservice.patient.service;


import com.fg.patientservice.exception.PatientNotFoundException;
import com.fg.patientservice.patient.dto.PatientCreateDTO;
import com.fg.patientservice.patient.dto.PatientDTO;
import com.fg.patientservice.patient.mapper.PatientMapper;
import com.fg.patientservice.patient.model.Patient;
import com.fg.patientservice.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    
    @Override
    public PatientDTO createPatient(PatientCreateDTO patientCreateDTO) {
        Patient patient = patientMapper.toPatient(patientCreateDTO);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toPatientDTO(savedPatient);
    }
    
    @Override
    public Optional<PatientDTO> getPatientById(UUID id) {
        return patientRepository.findById(id)
                .map(patientMapper::toPatientDTO);
    }
    
    @Override
    public Optional<PatientDTO> getPatientByUserId(UUID userId) {
        return patientRepository.findByUserId(userId)
                .map(patientMapper::toPatientDTO);
    }
    
    @Override
    public Optional<PatientDTO> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(patientMapper::toPatientDTO);
    }
    
    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toPatientDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public PatientDTO updatePatient(UUID id, PatientCreateDTO patientCreateDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));
        
        patient.setFirstName(patientCreateDTO.getFirstName());
        patient.setLastName(patientCreateDTO.getLastName());
        patient.setEmail(patientCreateDTO.getEmail());
        patient.setDateOfBirth(patientCreateDTO.getDateOfBirth());
        patient.setPhone(patientCreateDTO.getPhone());
        patient.setAddress(patientCreateDTO.getAddress());
        
        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toPatientDTO(updatedPatient);
    }
    
    @Override
    public void deletePatient(UUID id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
    
    @Override
    public boolean existsByUserId(UUID userId) {
        return patientRepository.existsByUserId(userId);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return patientRepository.existsByEmail(email);
    }
}