package com.fg.clinicservice.service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDto {
    private UUID serviceId;
    private String serviceName;
    private String description;
    private String category;
    private BigDecimal price;
    private LocalTime duration;
    private Boolean requiresPrescription;
    private Boolean active;
}
