package com.fg.clinicservice.controller;


import com.fg.clinicservice.clinic.dto.ClinicDTO;
import com.fg.clinicservice.clinic.dto.ClinicOwnerDTO;
import com.fg.clinicservice.clinic.dto.CreateClinicOwnerRequest;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.clinic.service.IClinicService;
import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service.model.ServiceDto;
import com.fg.clinicservice.service.model.ServiceForm;
import com.fg.clinicservice.service.service.IService;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementDto;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementForm;
import com.fg.clinicservice.special_requirement.service.SpecialRequirementService;
import com.fg.clinicservice.speciality.model.SpecialityDto;
import com.fg.clinicservice.speciality.model.SpecialityForm;
import com.fg.clinicservice.speciality.service.ISpecialityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private IClinicService iClinicService;
    private SpecialRequirementService specialRequirementService;
    private IService iService;
    private ISpecialityService iSpecialityService;

    public AdminController(IClinicService iClinicService,
                           SpecialRequirementService specialRequirementService,
                           IService iService,
                           ISpecialityService iSpecialityService
    ) {
        this.iClinicService = iClinicService;
        this.specialRequirementService = specialRequirementService;
        this.iService = iService;
        this.iSpecialityService = iSpecialityService;
    }

    @Operation(summary = "Create new clinic owner")
    @PostMapping("/clinic-owners")
    public ResponseData<ClinicOwnerDTO> createClinicOwner(@RequestBody CreateClinicOwnerRequest request) {
        return iClinicService.createClinicOwner(request);
    }

    @Operation(summary = "Create new clinic for owner")
    @PostMapping("/create-clinic")
    public ResponseEntity<ResponseData<ClinicDTO>> CreateClinic(@RequestBody ClinicForm clinicForm) {
        ResponseData<ClinicDTO> response = iClinicService.createNewClinic(clinicForm);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add new special requirement for service")
    @PostMapping("/sr/add-new")
    public ResponseEntity<ResponseData<SpecialRequirementDto>> addNewSpecialRequirement(
            @RequestBody SpecialRequirementForm specialRequirementForm) {
        ResponseData<SpecialRequirementDto> response = specialRequirementService.createNewSpecialRequirement(specialRequirementForm);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update special requirement")
    @PutMapping("/sr/update/{id}")
    public ResponseEntity<ResponseData<SpecialRequirementDto>> updateSpecialRequirement(
            @PathVariable UUID id, @RequestBody SpecialRequirementForm form) {
        ResponseData<SpecialRequirementDto> response = specialRequirementService.updateSpecialRequirement(id, form);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove special request for serviceId")
    @DeleteMapping("/sr/del/{id}")
    public ResponseEntity<ResponseData<String>> deleteSpecialRequirement(@PathVariable UUID id) {
        ResponseData<String> response = specialRequirementService.deleteByServiceId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create new service")
    @PostMapping("/service/create")
    ResponseEntity<ResponseData<ServiceDto>> createNewService(@RequestBody ServiceForm serviceForm) {
        ResponseData<ServiceDto> responseData = iService.createService(serviceForm);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Update information for service")
    @PutMapping("/service/update/{id}")
    ResponseEntity<ResponseData<ServiceDto>> updateService(@PathVariable UUID id, @RequestBody ServiceForm serviceForm) {
        ResponseData<ServiceDto> responseData = iService.updateService(id, serviceForm);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "create new spciality")
    @PostMapping("/speciality/create")
    ResponseEntity<ResponseData<SpecialityDto>> createSpeciality(@RequestBody SpecialityForm specialityForm) {
        ResponseData<SpecialityDto> responseData = iSpecialityService.createSpeciality(specialityForm);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Update information for speciality")
    @PutMapping("/speciality/update/{id}")
    ResponseEntity<ResponseData<SpecialityDto>> updateSpeciality(@PathVariable UUID id ,@RequestBody SpecialityForm specialityForm) {
        ResponseData<SpecialityDto> responseData = iSpecialityService.updateSpeciality(id, specialityForm);
        return ResponseEntity.ok(responseData);
    }
}
