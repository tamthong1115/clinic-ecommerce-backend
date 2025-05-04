package com.fg.clinicservice.clinic_service.service;

import com.fg.clinicservice.clinic_service.model.ClinicServiceDto;
import com.fg.clinicservice.clinic_service.model.ClinicServiceForm;
import com.fg.clinicservice.response.ResponseData;

import java.util.List;
import java.util.UUID;

public interface IClinicService {
    ResponseData<ClinicServiceDto> createClinic(ClinicServiceForm clinicServiceForm);
    ResponseData<String> update(ClinicServiceForm clinicServiceForm);
    ResponseData<List<ClinicServiceDto>> getByService(UUID serviceId);
    ResponseData<List<ClinicServiceDto>> getByClinic(UUID clinicId);
}
