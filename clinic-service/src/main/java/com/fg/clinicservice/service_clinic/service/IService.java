package com.fg.clinicservice.service_clinic.service;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service_clinic.model.ServiceDto;
import com.fg.clinicservice.service_clinic.model.ServiceForm;
import com.fg.clinicservice.service_clinic.model.ServiceSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface IService {
    ResponseData<ServiceDto> createService(ServiceForm form);
    ResponseData<ServiceDto> updateService(UUID serviceId, ServiceForm Form);
    ResponseData<ServiceDto> getServiceById(UUID serviceId);
    @Transactional(readOnly = true)
    Page<ServiceDto> searchServices(ServiceSearchCriteria criteria, Pageable pageable);
    Page<ServiceDto> getAllServices(Pageable pageable);
    Page<ServiceDto> getAllServiceBySpeciality(UUID specialityId, Pageable pageable);
}
