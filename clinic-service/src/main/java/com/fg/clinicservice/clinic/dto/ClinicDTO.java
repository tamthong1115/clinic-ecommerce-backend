package com.fg.clinicservice.clinic.dto;

import com.fg.clinicservice.clinic.model.Clinic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicDTO {
    private UUID clinicId;
    private UUID ownerId;
    private String ownerName;
    private String clinicName;
    private String email;
    private String clinicPhone;
    private String clinicAddress;
    private String description;
    private String image;
    private Clinic.Status status;
}