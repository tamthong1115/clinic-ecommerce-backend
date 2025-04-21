package com.fg.clinicservice.clinic_service.model;

import org.springframework.stereotype.Component;

@Component
public class ClinicServiceMapper {

    public static ClinicServiceDto toDto(ClinicService clinicService) {
        if (clinicService == null) return null;

        ClinicServiceDto dto = new ClinicServiceDto();
        dto.setClinicId(clinicService.getClinic().getClinicId());
        dto.setServiceId(clinicService.getService().getServiceId());
        dto.setStatus(clinicService.getStatus().name());
        return dto;
    }


}