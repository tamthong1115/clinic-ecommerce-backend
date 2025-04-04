package com.fg.clinicservice.service.service;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service.model.EService;
import com.fg.clinicservice.service.model.ServiceForm;
import com.fg.clinicservice.service.model.ServiceDto;
import com.fg.clinicservice.service.model.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements IService {

    @Autowired
    ServiceRepository serviceRepository;

    public ServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ResponseData<ServiceDto> createService(ServiceForm form) {
        EService createService = ServiceMapper.formForm(form);
        EService saveService = serviceRepository.save(createService);
        ServiceDto serviceDto = ServiceMapper.toDto(saveService);
        return new ResponseData<>(201, "Service created successfully", serviceDto);

    }

    @Override
    public ResponseData<ServiceDto> updateService(UUID serviceId, ServiceForm Form) {
        EService service = serviceRepository.findById(serviceId).orElseThrow(()-> new RuntimeException("Service not found"));
        ServiceMapper.updateServiceForm(service, Form);
        EService saveService = serviceRepository.save(service);
        ServiceDto serviceDto = ServiceMapper.toDto(saveService);
        return new ResponseData<>(201, "Service updated successfully", serviceDto);
    }

    @Override
    public ResponseData<ServiceDto> getServiceById(UUID serviceId) {
        EService service = serviceRepository.findById(serviceId).orElseThrow(()-> new RuntimeException("Service not found"));
        ServiceDto serviceDto = ServiceMapper.toDto(service);
        return new ResponseData<>(201, "Service found", serviceDto);
    }

    @Override
    public ResponseData<List<ServiceDto>> getAllService() {
        List<ServiceDto> listServices = serviceRepository.findAll().stream()
                .map(ServiceMapper::toDto)
                .collect(Collectors.toList());
        return  new ResponseData<>(201,"Service get successfully", listServices);
    }
}
