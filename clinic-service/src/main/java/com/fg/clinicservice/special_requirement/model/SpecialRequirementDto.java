package com.fg.clinicservice.special_requirement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialRequirementDto {
    private UUID id;
    private UUID serviceId;
    private String requirement;
}
