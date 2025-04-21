package com.fg.clinicservice.special_requirement.service;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.special_requirement.model.SpecialRequirement;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementDto;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementForm;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface SpecialRequirementService {
    ResponseData<SpecialRequirementDto> findBySRId(UUID id);
    ResponseData<List<SpecialRequirementDto>> findByServiceId(UUID serviceId);
    ResponseData<String> deleteById(UUID id);
    ResponseData<String> deleteByServiceId(UUID serviceId);
    ResponseData<SpecialRequirementDto> createNewSpecialRequirement(SpecialRequirementForm form);
    ResponseData<SpecialRequirementDto> updateSpecialRequirement(UUID id, SpecialRequirementForm form);
}
