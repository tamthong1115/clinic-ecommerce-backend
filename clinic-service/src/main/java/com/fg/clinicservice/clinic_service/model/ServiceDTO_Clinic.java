package com.fg.clinicservice.clinic_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceDTO_Clinic {
    private UUID serviceId;
    private String serviceName;
    private UUID specialityId;
    private String specialityName;
    private String status;
}
