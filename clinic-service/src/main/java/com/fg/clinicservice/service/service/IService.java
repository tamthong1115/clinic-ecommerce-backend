package com.fg.clinicservice.service.service;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service.model.ServiceDto;
import com.fg.clinicservice.service.model.ServiceForm;

import java.util.List;
import java.util.UUID;

public interface IService {
    ResponseData<ServiceDto> createService(ServiceForm form);
    ResponseData<ServiceDto> updateService(UUID serviceId, ServiceForm Form);
    ResponseData<ServiceDto> getServiceById(UUID serviceId);
    ResponseData<List<ServiceDto>> getAllService();
    ResponseData<List<ServiceDto>> getAllServiceBySpeciality(UUID specialityId);
}
