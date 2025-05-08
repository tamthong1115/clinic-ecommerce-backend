package com.fg.clinicservice.controller;



import com.fg.clinicservice.clinic.dto.ClinicDTO;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.clinic.model.ClinicRequest;
import com.fg.clinicservice.clinic_service.model.ClinicServiceDto;
import com.fg.clinicservice.clinic_service.model.ClinicServiceForm;
import com.fg.clinicservice.doctor.dto.DoctorBasicResponse;
import com.fg.clinicservice.doctor.dto.DoctorDetailResponse;
import com.fg.clinicservice.doctor.dto.DoctorRequest;
import com.fg.clinicservice.doctor.dto.DoctorSearchCriteria;
import com.fg.clinicservice.doctor.service.DoctorService;
import com.fg.clinicservice.clinic_service.model.ServiceDTO_Clinic;
import com.fg.clinicservice.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {

    private com.fg.clinicservice.clinic.service.IClinicService iClinicService1;
    private com.fg.clinicservice.clinic_service.service.IClinicService iClinicService2;
    private final DoctorService doctorService;

    public ClinicController(
            com.fg.clinicservice.clinic.service.IClinicService iClinicService1,
            com.fg.clinicservice.clinic_service.service.IClinicService iClinicService2, DoctorService doctorService
    ) {
        this.iClinicService1 = iClinicService1;
        this.iClinicService2 = iClinicService2;
        this.doctorService = doctorService;
    }


    @PostMapping("/{clinic_id}/create-doctor")
    public ResponseEntity<DoctorDetailResponse> createDoctorWithClinicId(
            @PathVariable UUID clinic_id,
            @Valid @ModelAttribute DoctorRequest doctorRequest) {
        return new ResponseEntity<>(doctorService.createDoctor(clinic_id, doctorRequest), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<DoctorDetailResponse> createDoctorWithoutClinicId(
            @Valid @RequestBody DoctorRequest doctorRequest) {
        return new ResponseEntity<>(doctorService.createDoctor(null, doctorRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{clinic_id}/doctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get clinics by owner Id with pagination")
    @GetMapping("/get-by-owner")
    public ResponseEntity<ResponseData<Page<ClinicDTO>>> getClinicsByOwnerPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "clinicName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        ResponseData<Page<ClinicDTO>> response = iClinicService1.getClinicsByOwnerIdWithPagination(page, size, sortBy, direction);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update information for clinic")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<ClinicDTO>> updateClinic(@PathVariable UUID id, @ModelAttribute  ClinicForm clinicForm) {
        ResponseData<ClinicDTO> response = iClinicService1.updateClinic(id,clinicForm);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update status of clinic")
    @PatchMapping("/set-status/{id}")
    public ResponseEntity<ResponseData<String>> updateClinicStatus(
            @PathVariable UUID id,
            @RequestBody ClinicRequest request
    ) {
        ResponseData<String> response = iClinicService1.updateClinicStatus(id, request.getStatus());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add new service for clinic")
    @PostMapping("/service-for-clinic/add")
    public ResponseEntity<ResponseData<ClinicServiceDto>> addNewServiceToClinic(@RequestBody ClinicServiceForm clinicServiceForm) {
        ResponseData<ClinicServiceDto> response = iClinicService2.createClinic(clinicServiceForm);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "update status for service in clinic")
    @PutMapping("/service-for-clinic/status")
    public ResponseEntity<ResponseData<String>> updateServiceStatus(@RequestBody ClinicServiceForm clinicServiceForm) {
        ResponseData<String> responseData = iClinicService2.update(clinicServiceForm);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "get all service in clinic")
    @GetMapping("/get-service/{id}")
    public ResponseEntity<ResponseData<List<ServiceDTO_Clinic>>> getServiceById(@PathVariable UUID id) {
        ResponseData<List<ServiceDTO_Clinic>> response = iClinicService2.getByClinic(id);
        return  ResponseEntity.ok(response);
    }

@GetMapping("/doctors")
public ResponseEntity<Page<DoctorBasicResponse>> getAllDoctors(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "lastName") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

    Page<DoctorBasicResponse> doctors = doctorService.getAllDoctorsBasic(page, size, sortBy, direction);
    return ResponseEntity.ok(doctors);
}

    @Operation(summary = "Search doctors with filters")
    @GetMapping("/search-doctors")
    public ResponseEntity<Page<DoctorBasicResponse>> searchDoctors(
            DoctorSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        // Set pagination parameters in the criteria object
        criteria.setPage(page);
        criteria.setSize(size);
        criteria.setSortBy(sortBy);
        criteria.setSortDirection(Sort.Direction.fromString(direction));

        Page<DoctorBasicResponse> searchResults = doctorService.searchDoctorsBasic(criteria);
        return ResponseEntity.ok(searchResults);
    }

@GetMapping("/{clinic_id}/doctors")
public ResponseEntity<Page<DoctorBasicResponse>> getDoctorsByClinic(
        @PathVariable UUID clinic_id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "lastName") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {

    Page<DoctorBasicResponse> doctors = doctorService.getDoctorsByClinicBasic(clinic_id, page, size, sortBy, direction);
    return ResponseEntity.ok(doctors);
}

@GetMapping("/doctor/{id}")
public ResponseEntity<DoctorDetailResponse> getDoctorDetails(@PathVariable UUID id) {
    DoctorDetailResponse doctorDetail = doctorService.getDoctorById(id);
    return ResponseEntity.ok(doctorDetail);
}
}
