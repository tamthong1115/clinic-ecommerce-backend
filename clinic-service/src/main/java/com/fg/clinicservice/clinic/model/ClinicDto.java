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
    private UUID clinicId;
    private UUID userId;
    private String userName;
    private String clinicName;
    private String email;
    private String clinicPhone;
    private String clinicAddress;
    private String description;
    private String image;
    private Clinic.Status status;
}
