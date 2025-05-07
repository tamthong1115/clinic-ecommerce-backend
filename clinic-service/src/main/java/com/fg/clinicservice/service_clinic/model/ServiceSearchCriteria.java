package com.fg.clinicservice.service_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSearchCriteria {
    private UUID specialty;
    private UUID clinicId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean isActive;
    private String searchTerm;
}