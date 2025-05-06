package com.fg.clinicservice.service_clinic.service;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service_clinic.model.EService;
import com.fg.clinicservice.service_clinic.model.ServiceForm;
import com.fg.clinicservice.service_clinic.model.ServiceDto;
import com.fg.clinicservice.service_clinic.model.ServiceMapper;
import com.fg.clinicservice.speciality.model.Speciality;
import com.fg.clinicservice.speciality.service.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements IService {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    SpecialityRepository specialityRepository;

    public ServiceImpl(ServiceRepository serviceRepository, SpecialityRepository specialityRepository) {
        this.serviceRepository = serviceRepository;
        this.specialityRepository = specialityRepository;
    }

    @Override
    public ResponseData<ServiceDto> createService(ServiceForm form) {

        Speciality speciality = specialityRepository.findById(form.getSpecialityId())
                .orElseThrow(() -> new RuntimeException("Chuyên khoa không tồn tại"));

        EService createService = ServiceMapper.formForm(form,speciality);
        EService saveService = serviceRepository.save(createService);
        ServiceDto serviceDto = ServiceMapper.toDto(saveService);
        return new ResponseData<>(201, "Service created successfully", serviceDto);

    }

    @Override
    public ResponseData<ServiceDto> updateService(UUID serviceId, ServiceForm form) {
        EService service = serviceRepository.findById(serviceId)
                .orElseThrow(()-> new RuntimeException("Service not found"));

        Speciality speciality = null;
        if (form.getSpecialityId() != null) {
            speciality = specialityRepository.findById(form.getSpecialityId())
                    .orElseThrow(() -> new RuntimeException("Chuyên khoa không tồn tại"));
        }
        ServiceMapper.updateServiceForm(service, form, speciality);
        EService saveService = serviceRepository.save(service);
        ServiceDto serviceDto = ServiceMapper.toDto(saveService);
        return new ResponseData<>(200, "Service updated successfully", serviceDto);
    }

    @Override
    public ResponseData<ServiceDto> getServiceById(UUID serviceId) {
        EService service = serviceRepository.findById(serviceId).orElseThrow(()-> new RuntimeException("Service not found"));
        ServiceDto serviceDto = ServiceMapper.toDto(service);
        return new ResponseData<>(200, "Service found", serviceDto);
    }

    @Override
    public Page<ServiceDto> getAllServices(Pageable pageable) {
        Page<EService> servicePage = serviceRepository.findAll(pageable);
        return servicePage.map(ServiceMapper::toDto);
    }

    @Override
    public Page<ServiceDto> getAllServiceBySpeciality(UUID specialityId, Pageable pageable) {
        Page<EService> listServices = serviceRepository.findAllBySpecialityId(specialityId, pageable);
        return listServices.map(ServiceMapper::toDto);
    }
}
