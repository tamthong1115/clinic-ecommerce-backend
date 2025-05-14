package com.fg.authservice.client.patient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "patient-service", contextId = "patient-auth-client")
public interface PatientClient {
    @PostMapping("/api/v1/public")
    ResponseEntity<PatientDTO> createPatient(@RequestBody PatientCreateDTO patientCreateDTO);
}