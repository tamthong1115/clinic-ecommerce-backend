package com.fg.clinicservice.service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class ServiceForm {
    private String serviceName;
    private String description;
    private String category;
    private BigDecimal price;
    private LocalTime duration;
    private Boolean requiresPrescription;
    private Boolean active;
}
