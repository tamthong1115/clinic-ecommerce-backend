package com.fg.appointment.client.patient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "patient-service")
public interface PatientClient {

    @GetMapping("/api/v1/patient/by-user/{userId}")
    PatientIdResponse getPatientIdByUserId(@PathVariable UUID userId);

}