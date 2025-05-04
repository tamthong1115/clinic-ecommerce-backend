package com.fg.clinicservice.service_clinic.model;

import com.fg.clinicservice.speciality.model.Speciality;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {
    public static ServiceDto toDto(EService service) {
        if (service == null) return null;

        return ServiceDto.builder()
                .serviceId(service.getServiceId())
                .serviceName(service.getServiceName())
                .specialityId(service.getSpeciality() != null ? service.getSpeciality().getSpecialityId() : null)
                .specialityName(service.getSpeciality() != null ? service.getSpeciality().getSpecialityName() : null)
                .description(service.getDescription())
                .price(service.getPrice())
                .duration(service.getDuration())
                .requiresPrescription(service.isRequiresPrescription())
                .active(service.isActive())
                .build();
    }

    public static EService fromDto(ServiceDto serviceDto, Speciality speciality) {
        if (serviceDto == null) return null;

        return EService.builder()
                .serviceId(serviceDto.getServiceId())
                .serviceName(serviceDto.getServiceName())
                .description(serviceDto.getDescription())
                .speciality(speciality)
                .price(serviceDto.getPrice())
                .duration(serviceDto.getDuration())
                .requiresPrescription(serviceDto.getRequiresPrescription())
                .active(serviceDto.getActive())
                .build();
    }

    public static void updateServiceForm(EService service, ServiceForm form, Speciality speciality) {
        if (service == null || form == null) throw new IllegalArgumentException("Service or form cannot be null");

        if (form.getServiceName() != null) service.setServiceName(form.getServiceName());
        if (speciality != null) service.setSpeciality(speciality);
        if (form.getDescription() != null) service.setDescription(form.getDescription());
        if (form.getPrice() != null) service.setPrice(form.getPrice());
        if (form.getDuration() != null) service.setDuration(form.getDuration());
        if (form.getRequiresPrescription() != null) service.setRequiresPrescription(form.getRequiresPrescription());
        if (form.getActive() != null) service.setActive(form.getActive());
    }

    public static EService formForm(ServiceForm form, Speciality speciality) {
        if (form == null) throw new IllegalArgumentException("Form cannot be null");

        EService service = new EService();
        service.setServiceName(form.getServiceName());
        service.setSpeciality(speciality);
        service.setDescription(form.getDescription());
        service.setPrice(form.getPrice());
        service.setDuration(form.getDuration());
        service.setRequiresPrescription(form.getRequiresPrescription());
        service.setActive(form.getActive() != null ? form.getActive() : true);
        return service;
    }
}
