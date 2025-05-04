package com.fg.clinicservice.controller;

import com.fg.clinicservice.clinic.dto.ClinicDTO;
import com.fg.clinicservice.clinic.service.IClinicService;
import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service_clinic.model.ServiceDto;
import com.fg.clinicservice.service_clinic.service.IService;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementDto;
import com.fg.clinicservice.special_requirement.service.SpecialRequirementService;
import com.fg.clinicservice.speciality.model.SpecialityDto;
import com.fg.clinicservice.speciality.service.ISpecialityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    private IService iService;
    private IClinicService iClinicService;
    private SpecialRequirementService specialRequirementService;
    private ISpecialityService iSpecialityService;


    public PublicController(IService iService,
                            IClinicService iClinicService,
                            SpecialRequirementService specialRequirementService,
                            ISpecialityService iSpecialityService
    ) {
        this.iService = iService;
        this.iClinicService = iClinicService;
        this.specialRequirementService = specialRequirementService;
        this.iSpecialityService = iSpecialityService;
    }

    @Operation(summary = "Get all service")
    @GetMapping("/get-all-service")
    ResponseEntity<ResponseData<List<ServiceDto>>> getAllServices() {
        ResponseData<List<ServiceDto>> listService = iService.getAllService();
        return ResponseEntity.ok(listService);
    }

    @Operation(summary = "Get service by ID")
    @GetMapping("/service/{id}")
    ResponseEntity<ResponseData<ServiceDto>> getServiceById(@PathVariable UUID id) {
        ResponseData<ServiceDto> responseData = iService.getServiceById(id);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get all clinic")
    @GetMapping("/get-all-clinic")
    ResponseEntity<ResponseData<List<ClinicDTO>>> getAllClinics() {
        ResponseData<List<ClinicDTO>> responseData = iClinicService.getAllClinics();
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get clinic by id")
    @GetMapping("/clinic/{id}")
    ResponseEntity<ResponseData<ClinicDTO>> getClinicById(@PathVariable UUID id) {
        ResponseData<ClinicDTO> responseData = iClinicService.getClinicById(id);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get all by service")
    @GetMapping("/get-sr/{id}")
    public ResponseEntity<ResponseData<List<SpecialRequirementDto>>> getSpecialRequirementByServiceId(
            @PathVariable UUID id) {
        ResponseData<List<SpecialRequirementDto>> response = specialRequirementService.findByServiceId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all speciality")
    @GetMapping("/speciality/get-all")
    public ResponseEntity<ResponseData<List<SpecialityDto>>> getAllSpeciality() {
        ResponseData<List<SpecialityDto>> responseData = iSpecialityService.getAllSpeciality();
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get speciality by id")
    @GetMapping("/speciality/{id}")
    public ResponseEntity<ResponseData<SpecialityDto>> getSpecialityById(@PathVariable UUID id) {
        ResponseData<SpecialityDto> responseData = iSpecialityService.getSpecialityById(id);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get service by specialityId")
    @GetMapping("/service-by-specid/{id}")
    public ResponseEntity<ResponseData<List<ServiceDto>>> getServiceBySpecialityId(@PathVariable UUID id) {
        ResponseData<List<ServiceDto>> responseData = iService.getAllServiceBySpeciality(id);
        return ResponseEntity.ok(responseData);
    }
}
