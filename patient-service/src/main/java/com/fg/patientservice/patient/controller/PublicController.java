package com.fg.patientservice.patient.controller;

import com.fg.patientservice.patient.service.PatientService;
import com.fg.patientservice.patient.dto.PatientCreateDTO;
import com.fg.patientservice.patient.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {
    private final PatientService patientService;


    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientCreateDTO patientCreateDTO) {
        return ResponseEntity.ok(patientService.createPatient(patientCreateDTO));
    }
}
