package com.fg.clinicservice.service_clinic.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ServiceSearchCriteria {
    private String doctorName;
    private UUID clinicId;
    private String specialty;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean isActive;
    private String searchTerm; // For general search
}