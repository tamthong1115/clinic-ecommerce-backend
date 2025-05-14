package com.fg.clinicservice.controller;

import com.fg.clinicservice.clinic.dto.ClinicDTO;
import com.fg.clinicservice.clinic.service.IClinicService;
import com.fg.clinicservice.doctor.dto.DoctorBasicResponse;
import com.fg.clinicservice.doctor.dto.DoctorDetailResponse;
import com.fg.clinicservice.doctor.dto.DoctorSearchCriteria;
import com.fg.clinicservice.doctor.dto.DoctorSearchResponse;
import com.fg.clinicservice.doctor.service.DoctorService;
import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service_clinic.model.ServiceDto;
import com.fg.clinicservice.service_clinic.model.ServiceSearchCriteria;
import com.fg.clinicservice.service_clinic.service.IService;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementDto;
import com.fg.clinicservice.special_requirement.service.SpecialRequirementService;
import com.fg.clinicservice.speciality.model.SpecialityDto;
import com.fg.clinicservice.speciality.service.ISpecialityService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    private IService iService;
    private IClinicService iClinicService;
    private SpecialRequirementService specialRequirementService;
    private ISpecialityService iSpecialityService;
    private final DoctorService doctorService;


    public PublicController(IService iService,
                            IClinicService iClinicService,
                            SpecialRequirementService specialRequirementService,
                            ISpecialityService iSpecialityService, DoctorService doctorService
    ) {
        this.iService = iService;
        this.iClinicService = iClinicService;
        this.specialRequirementService = specialRequirementService;
        this.iSpecialityService = iSpecialityService;
        this.doctorService = doctorService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailResponse> getDoctorById(@PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<List<DoctorBasicResponse>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @Operation(summary = "Get all service")
    @GetMapping("/get-all-service")
    public ResponseEntity<ResponseData<Page<ServiceDto>>> getAllServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Page<ServiceDto> servicePage = iService.getAllServices(PageRequest.of(page, size));
        return ResponseEntity.ok(new ResponseData<>(200, "Services retrieved successfully", servicePage));
    }

    @Operation(summary = "Get service by ID")
    @GetMapping("/service/{id}")
    ResponseEntity<ResponseData<ServiceDto>> getServiceById(@PathVariable UUID id) {
        ResponseData<ServiceDto> responseData = iService.getServiceById(id);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get all clinics with pagination")
    @GetMapping("/get-all-clinic")
    public ResponseEntity<ResponseData<Page<ClinicDTO>>> getAllClinics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "clinicName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        ResponseData<Page<ClinicDTO>> response = iClinicService.getAllClinics(page, size, sortBy, direction);
        return ResponseEntity.ok(response);
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
    public ResponseEntity<ResponseData<Page<SpecialityDto>>> getAllSpeciality(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "12") int size
    ) {
        Page<SpecialityDto> responseData = iSpecialityService.getAllSpeciality(PageRequest.of(page, size));
        return ResponseEntity.ok(new ResponseData<>(200, "Specialities retrieved successfully", responseData));
    }

    @Operation(summary = "Get speciality by id")
    @GetMapping("/speciality/{id}")
    public ResponseEntity<ResponseData<SpecialityDto>> getSpecialityById(@PathVariable UUID id) {
        ResponseData<SpecialityDto> responseData = iSpecialityService.getSpecialityById(id);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get service by specialityId")
    @GetMapping("/service-by-specid/{id}")
    public ResponseEntity<ResponseData<Page<ServiceDto>>> getServiceBySpecialityId(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Page<ServiceDto> responseData = iService.getAllServiceBySpeciality(id, PageRequest.of(page, size));
        return ResponseEntity.ok(new ResponseData<>(200, "Get service by specialityId successfully", responseData));
    }

    @Operation(summary = "Search service with filter")
    @GetMapping("/search-service")
    public ResponseEntity<ResponseData<Page<ServiceDto>>> searchService(
            ServiceSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        Page<ServiceDto> searchResults = iService.searchServices(criteria, PageRequest.of(page, size));
        return ResponseEntity.ok(new ResponseData<>(200, "Search service successfully", searchResults));
    }

    @Operation(summary = "Search doctors with filters")
    @GetMapping("/search-doctors")
    public ResponseEntity<ResponseData<Page<DoctorSearchResponse>>> searchDoctors(
            DoctorSearchCriteria criteria) {
        Page<DoctorSearchResponse> searchResults = doctorService.searchDoctors(criteria);
        return ResponseEntity.ok(new ResponseData<>(200, "Search doctors successfully", searchResults));
    }
}
