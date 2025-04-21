package com.fg.clinicservice.service.model;

import org.springframework.stereotype.Component;

@Component
public class ServiceMapper {
    public static ServiceDto toDto (EService service) {
        if(service == null) return null;

        return ServiceDto.builder()
                .serviceId(service.getServiceId())
                .serviceName(service.getServiceName())
                .description(service.getDescription())
                .category(service.getCategory())
                .price(service.getPrice())
                .duration(service.getDuration())
                .requiresPrescription(service.isRequiresPrescription())
                .build();
    }

    public static EService fromDto(ServiceDto serviceDto) {
        if (serviceDto == null) return null;

        return EService.builder()
                .serviceId(serviceDto.getServiceId()) // Gán ID nếu có
                .serviceName(serviceDto.getServiceName())
                .description(serviceDto.getDescription())
                .category(serviceDto.getCategory())
                .price(serviceDto.getPrice())
                .duration(serviceDto.getDuration())
                .requiresPrescription(serviceDto.getRequiresPrescription()) // Không dùng isRequiresPrescription()
                .active(serviceDto.getActive()) // Đảm bảo trường active được gán
                .build();
    }

    public static void updateServiceForm(EService service, ServiceForm serviceForm) {
        if (service == null) throw new IllegalArgumentException("Service cannot be null");
        if (serviceForm == null) throw new IllegalArgumentException("ServiceForm cannot be null");

        if(serviceForm.getServiceName() != null) service.setServiceName(serviceForm.getServiceName());
        if(serviceForm.getDescription() != null) service.setDescription(serviceForm.getDescription());
        if(serviceForm.getCategory() != null) service.setCategory(serviceForm.getCategory());
        if(serviceForm.getPrice() != null) service.setPrice(serviceForm.getPrice());
        if(serviceForm.getDuration() != null) service.setDuration(serviceForm.getDuration());
        if (serviceForm.getRequiresPrescription() != null) service.setRequiresPrescription(serviceForm.getRequiresPrescription());
        if (serviceForm.getActive() != null) service.setActive(serviceForm.getActive());
    }

    public static EService formForm(ServiceForm form) {
        if(form == null) throw new IllegalArgumentException("Form cannot be null");

        EService service = new EService();
        service.setServiceName(form.getServiceName());
        service.setDescription(form.getDescription());
        service.setCategory(form.getCategory());
        service.setPrice(form.getPrice());
        service.setDuration(form.getDuration());
        service.setRequiresPrescription(form.getRequiresPrescription());
        service.setActive(true);

        return service;
    }
}
