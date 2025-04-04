package com.fg.clinicservice.special_requirement.model;

import org.springframework.stereotype.Component;

@Component
public class SpecialRequirementMapper {
    public static SpecialRequirementDto toDto(SpecialRequirement specialRequirement) {
        if (specialRequirement == null) return null;

        return SpecialRequirementDto.builder()
                .id(specialRequirement.getId())
                .serviceId(specialRequirement.getService().getServiceId())
                .requirement(specialRequirement.getRequirement())
                .build();
    }


    public static SpecialRequirement fromDto (SpecialRequirementDto specialRequirementDto) {
        if (specialRequirementDto == null) return null;

        SpecialRequirement specialRequirement = new SpecialRequirement();
        specialRequirement.setId(specialRequirementDto.getId());
        specialRequirement.setRequirement(specialRequirementDto.getRequirement());
        specialRequirement.setServiceId(specialRequirementDto.getServiceId());

        return specialRequirement;
    }
}
