package com.fg.clinicservice.controller;

import com.fg.clinicservice.clinic_service.service.IClinicService;
import com.fg.clinicservice.clinic_service.model.ClinicServiceDto;
import com.fg.clinicservice.clinic_service.model.ClinicServiceForm;
import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service.service.IService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinic/services")
public class ClinicServiceController {
    private IClinicService iClinicService;

    public ClinicServiceController(IClinicService iClinicService) {
        this.iClinicService = iClinicService;
    }

    @Operation(summary = "Add new service for clinic")
    @PostMapping("/add")
    public ResponseEntity<ResponseData<ClinicServiceDto>> addNewServiceToClinic(@RequestBody ClinicServiceForm clinicServiceForm) {
        ResponseData<ClinicServiceDto> response = iClinicService.createClinic(clinicServiceForm);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "update status for service in clinic")
    @PutMapping("/status")
    public ResponseEntity<ResponseData<String>> updateServiceStatus(@RequestBody ClinicServiceForm clinicServiceForm) {
        ResponseData<String> responseData = iClinicService.update(clinicServiceForm);
        return ResponseEntity.ok(responseData);
    }
}
