package com.fg.clinicservice.clinic_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicServiceDto {
    private UUID clinicId;
    private UUID serviceId;
    private String status;
    private String clinicName;
    private String serviceName;
    private UUID specialtyId;
    private String specialtyName;
}
