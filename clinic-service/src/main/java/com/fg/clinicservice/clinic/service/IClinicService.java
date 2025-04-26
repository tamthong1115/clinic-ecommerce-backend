package com.fg.clinicservice.clinic.service;

import com.fg.clinicservice.clinic.dto.ClinicOwnerDTO;
import com.fg.clinicservice.clinic.dto.CreateClinicOwnerRequest;
import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.clinic.dto.ClinicDTO;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.response.ResponseData;

import java.util.List;
import java.util.UUID;

public interface IClinicService {
    ResponseData<ClinicOwnerDTO> createClinicOwner(CreateClinicOwnerRequest request);
    ResponseData<ClinicDTO> createNewClinic(ClinicForm clinicForm);
    ResponseData<ClinicDTO> updateClinic(UUID clinicId , ClinicForm clinicForm);
    ResponseData<ClinicDTO> getClinicById(UUID clinicId);
    ResponseData<String> updateClinicStatus(UUID clinicId, Clinic.Status status);
    ResponseData<List<ClinicDTO>> getAllClinics();
}
