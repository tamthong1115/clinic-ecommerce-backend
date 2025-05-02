package com.fg.clinicservice.speciality.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityDto {
    private UUID specialityId;
    private String specialityName;
    private String specialityDescription;
    private String specialityImageUrl;
}
