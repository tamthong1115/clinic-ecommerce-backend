package com.fg.clinicservice.special_requirement.model;

import lombok.Data;

import java.util.UUID;

@Data
public class SpecialRequirementForm {
    private UUID serviceId;
    private String requirement;
}
