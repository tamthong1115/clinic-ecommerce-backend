package com.fg.clinicservice.clinic.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicDto {
    private String clinicName;
    private String clinicAddress;
    private String clinicPhone;
    private String status;
    private UUID userId;
}
