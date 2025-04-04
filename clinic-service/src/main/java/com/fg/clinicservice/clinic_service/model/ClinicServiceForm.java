package com.fg.clinicservice.clinic_service.model;
import com.fg.clinicservice.clinic_service.model.ClinicService.Status;

import lombok.Data;

import java.util.UUID;

@Data
public class ClinicServiceForm {
    private UUID clinicId;
    private UUID serviceId;
    private Status status;
}
