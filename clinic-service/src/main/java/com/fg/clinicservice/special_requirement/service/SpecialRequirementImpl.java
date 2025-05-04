package com.fg.clinicservice.special_requirement.service;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service_clinic.model.EService;
import com.fg.clinicservice.service_clinic.service.ServiceRepository;
import com.fg.clinicservice.special_requirement.model.SpecialRequirement;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementDto;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementForm;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SpecialRequirementImpl implements SpecialRequirementService {

    @Autowired
    SpecialRequirementRepository specialRequirementRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public SpecialRequirementImpl(SpecialRequirementRepository specialRequirementRepository) {
        this.specialRequirementRepository = specialRequirementRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ResponseData<SpecialRequirementDto> findBySRId(UUID id) {
        SpecialRequirement specialRequirement = specialRequirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Special Requirement not found"));
        SpecialRequirementDto specialRequirementDto = SpecialRequirementMapper.toDto(specialRequirement);

        return new ResponseData<>(200,"Get special requirement successfully", specialRequirementDto);
    }

    @Override
    public ResponseData<List<SpecialRequirementDto>> findByServiceId(UUID serviceId) {
        List<SpecialRequirementDto> listSR = specialRequirementRepository.findByServiceId(serviceId).stream()
                .map(SpecialRequirementMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseData<>(200,"Get special requirement successfully", listSR);
    }

    @Override
    public ResponseData<String> deleteById(UUID id) {
        SpecialRequirement exsit = specialRequirementRepository.findById(id).orElseThrow(() -> new RuntimeException("Special Requirement not found"));
        specialRequirementRepository.deleteById(id);
        return new ResponseData<>(200,"Delete special requirement successfully");
    }

    @Override
    public ResponseData<String> deleteByServiceId(UUID serviceId) {
        List<SpecialRequirement> listRQ = specialRequirementRepository.findByServiceId(serviceId);
        if(listRQ.isEmpty()){
            throw new RuntimeException("No RQs found for the given serviceId: " + serviceId);
        }

        specialRequirementRepository.deleteByServiceId(serviceId);
        return new ResponseData<>(200,"All RQs for serviceId " + serviceId + " deleted successfully");
    }

    @Override
    public ResponseData<SpecialRequirementDto> createNewSpecialRequirement(SpecialRequirementForm form) {
        EService service = serviceRepository.findById(form.getServiceId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy service"));
        SpecialRequirement specialRequirement = new SpecialRequirement();
        specialRequirement.setService(service);
        specialRequirement.setRequirement(form.getRequirement());
        SpecialRequirement saved = specialRequirementRepository.save(specialRequirement);
        SpecialRequirementDto dto = SpecialRequirementMapper.toDto(saved);
        return new ResponseData<>(201, "Create special requirement successfully", dto);
    }

    @Override
    public ResponseData<SpecialRequirementDto> updateSpecialRequirement(UUID id, SpecialRequirementForm form) {
        SpecialRequirement exsit = specialRequirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Special Requirement not found"));
        exsit.setRequirement(form.getRequirement());
        specialRequirementRepository.save(exsit);
        SpecialRequirementDto specialRequirementDto = SpecialRequirementMapper.toDto(exsit);
        return new ResponseData<>(200,"Update special requirement successfully", specialRequirementDto);
    }
}
