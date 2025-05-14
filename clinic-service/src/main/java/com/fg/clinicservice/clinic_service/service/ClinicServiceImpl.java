package com.fg.clinicservice.clinic_service.service;

import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.clinic.model.ClinicMapper;
import com.fg.clinicservice.clinic.service.ClinicRepository;
import com.fg.clinicservice.clinic_service.model.*;
import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service_clinic.model.EService;
import com.fg.clinicservice.service_clinic.model.ServiceDto;
import com.fg.clinicservice.service_clinic.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClinicServiceImpl implements IClinicService {

    @Autowired
    ClinicServiceRepository clinicServiceRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public ClinicServiceImpl(ClinicServiceRepository clinicServiceRepository, ClinicRepository clinicRepository, ServiceRepository serviceRepository) {
        this.clinicServiceRepository = clinicServiceRepository;
        this.clinicRepository = clinicRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ResponseData<ClinicServiceDto> createClinic(ClinicServiceForm clinicServiceForm) {
        Clinic clinic = clinicRepository.findById(clinicServiceForm.getClinicId()).orElseThrow(()->new RuntimeException("Clinic not found"));
        EService service = serviceRepository.findById(clinicServiceForm.getServiceId()).orElseThrow(()->new RuntimeException("Service not found"));
        ClinicServiceId clinicServiceId = new ClinicServiceId(clinic.getClinicId(), service.getServiceId());

        ClinicService clinicService = new ClinicService();
        clinicService.setId(clinicServiceId);
        clinicService.setClinic(clinic);
        clinicService.setService(service);
        clinicService.setStatus(ClinicService.Status.ACTIVE);

        clinicServiceRepository.save(clinicService);

        ClinicServiceDto clinicServiceDto = ClinicServiceMapper.toDto(clinicService);
        return new ResponseData<>(201,"Clinic service created successfully", clinicServiceDto);
    }

    @Override
    public ResponseData<ClinicServiceDto> update(ClinicServiceForm clinicServiceForm) {
        Clinic clinic = clinicRepository.findById(clinicServiceForm.getClinicId()).orElseThrow(()->new RuntimeException("Clinic not found"));
        EService service = serviceRepository.findById(clinicServiceForm.getServiceId()).orElseThrow(()->new RuntimeException("Service not found"));
        ClinicServiceId clinicServiceId = new ClinicServiceId(clinic.getClinicId(), service.getServiceId());

        ClinicService clinicService = clinicServiceRepository.findById(clinicServiceId).get();
        clinicService.setStatus(clinicServiceForm.getStatus());
        clinicServiceRepository.save(clinicService);

        ClinicServiceDto item =  ClinicServiceMapper.toDto(clinicService);

        return  new ResponseData<>(200,"Clinic service updated successfully", item);

    }

    @Override
    public ResponseData<List<ClinicServiceDto>> getByService(UUID serviceId) {
        List<ClinicServiceDto> list = clinicServiceRepository.findByService_ServiceId(serviceId).stream()
                .map(ClinicServiceMapper::toDto)
                .collect(Collectors.toList());

        return  new ResponseData<>(200,"Clinic service get successfully", list);
    }

    @Override
    public ResponseData<List<ClinicServiceDto>> getByClinic(UUID clinicId) {
        List<ClinicService> list = clinicServiceRepository.findByClinic_ClinicId(clinicId);
        List<ClinicServiceDto> listService = list.stream()
                .map(item -> {
                    return ClinicServiceMapper.toDto(item);
                })
                .collect(Collectors.toList());

        return  new ResponseData<>(200,"Clinic service get successfully", listService);
    }
}
