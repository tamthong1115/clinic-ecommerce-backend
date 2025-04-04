package com.fg.clinicservice.clinic.service;

import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.clinic.model.ClinicDto;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.response.ResponseData;

import java.util.List;
import java.util.UUID;

public interface IClinicService {
    ResponseData<ClinicDto> createNewClinic(ClinicForm clinicForm);
    ResponseData<ClinicDto> updateClinic(UUID clinicId ,ClinicForm clinicForm);
    ResponseData<ClinicDto> getClinicById(UUID clinicId);
    ResponseData<String> updateClinicStatus(UUID clinicId, Clinic.Status status);
    ResponseData<List<ClinicDto>> getAllClinics();
}
