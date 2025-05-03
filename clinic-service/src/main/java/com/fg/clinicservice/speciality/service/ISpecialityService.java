package com.fg.clinicservice.speciality.service;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.speciality.model.SpecialityDto;
import com.fg.clinicservice.speciality.model.SpecialityForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface ISpecialityService {
    ResponseData<SpecialityDto> createSpeciality(SpecialityForm form);
    ResponseData<SpecialityDto> updateSpeciality(UUID specialityId, SpecialityForm form);
    ResponseData<SpecialityDto> getSpecialityById(UUID specialityId);
    ResponseData<List<SpecialityDto>> getAllSpeciality();
}
