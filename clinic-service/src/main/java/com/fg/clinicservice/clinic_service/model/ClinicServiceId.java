package com.fg.clinicservice.clinic_service.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicServiceId implements Serializable {
    private UUID clinicId;
    private UUID serviceId;
}
