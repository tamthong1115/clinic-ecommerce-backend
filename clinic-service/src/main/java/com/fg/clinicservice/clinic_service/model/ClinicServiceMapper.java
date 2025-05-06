package com.fg.clinicservice.clinic_service.model;

import com.fg.clinicservice.clinic_service.service.IClinicService;
import com.fg.clinicservice.service_clinic.model.EService;
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

    public static ServiceDTO_Clinic toCLinicDto(EService service, ClinicService clinicService) {
        if (service == null) return null;

        ServiceDTO_Clinic dto = new ServiceDTO_Clinic();
        dto.setServiceId(service.getServiceId());
        dto.setServiceName(service.getServiceName());
        dto.setSpecialityId(service.getSpeciality().getSpecialityId());
        dto.setSpecialityName(service.getSpeciality().getSpecialityName());
        dto.setStatus(clinicService.getStatus().name());
        return dto;
    }

}