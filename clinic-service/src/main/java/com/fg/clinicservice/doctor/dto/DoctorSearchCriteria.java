package com.fg.clinicservice.doctor.dto;

import lombok.Data;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class DoctorSearchCriteria {
    private String name;
    private UUID serviceId;
    private String serviceName;
    private UUID clinicId;
    private Integer minExperience;
    private Integer maxExperience;
    private Double minPrice;
    private Double maxPrice;
    private String dayOfWeek;
    private LocalDate date;
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "lastName";
    private Sort.Direction sortDirection = Sort.Direction.ASC;
}