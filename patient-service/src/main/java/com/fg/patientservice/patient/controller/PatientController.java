package com.fg.patientservice.patient.controller;

import com.fg.patientservice.exception.PatientNotFoundException;
import com.fg.patientservice.patient.dto.PatientCreateDTO;
import com.fg.patientservice.patient.dto.PatientDTO;
import com.fg.patientservice.patient.dto.PatientIdResponse;
import com.fg.patientservice.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable UUID id) {
        Optional<PatientDTO> patient = patientService.getPatientById(id);
        return patient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable UUID id, @RequestBody PatientCreateDTO patientCreateDTO) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientCreateDTO));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<PatientIdResponse> getPatientIdByUserId(@PathVariable UUID userId) {
        return patientService.getPatientByUserId(userId)
                .map(patientDTO -> new ResponseEntity<>(
                        new PatientIdResponse(patientDTO.getPatientId()), HttpStatus.OK))
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with userId: " + userId));
    }



}