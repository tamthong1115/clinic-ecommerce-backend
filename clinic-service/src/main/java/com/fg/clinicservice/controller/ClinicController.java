package com.fg.clinicservice.controller;



import com.fg.clinicservice.clinic.dto.ClinicDTO;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.clinic.model.ClinicRequest;
import com.fg.clinicservice.clinic_service.model.ClinicServiceDto;
import com.fg.clinicservice.clinic_service.model.ClinicServiceForm;
import com.fg.clinicservice.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {

    private com.fg.clinicservice.clinic.service.IClinicService iClinicService1;
    private com.fg.clinicservice.clinic_service.service.IClinicService iClinicService2;

    public ClinicController(
            com.fg.clinicservice.clinic.service.IClinicService iClinicService1,
            com.fg.clinicservice.clinic_service.service.IClinicService iClinicService2
    ) {
        this.iClinicService1 = iClinicService1;
        this.iClinicService2 = iClinicService2;
    }

    @Operation(summary = "Update information for clinic")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<ClinicDTO>> updateClinic(@PathVariable UUID id, @RequestBody ClinicForm clinicForm) {
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
}
