package com.fg.clinicservice.controller;



import com.fg.clinicservice.clinic.dto.ClinicDTO;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.clinic.model.ClinicRequest;
import com.fg.clinicservice.clinic.service.IClinicService;
import com.fg.clinicservice.response.ResponseData;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {

    private IClinicService iClinicService;

    public ClinicController(IClinicService iClinicService) {
        this.iClinicService = iClinicService;
    }

    @Operation(summary = "Create new clinic for owner")
    @PostMapping("/create")
    public ResponseEntity<ResponseData<ClinicDTO>> CreateClinic(@RequestBody ClinicForm clinicForm) {
        ResponseData<ClinicDTO> response = iClinicService.createNewClinic(clinicForm);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Update information for clinic")
    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseData<ClinicDTO>> updateClinic(@PathVariable UUID id, @RequestBody ClinicForm clinicForm) {
        ResponseData<ClinicDTO> response = iClinicService.updateClinic(id,clinicForm);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update status of clinic")
    @PatchMapping("/set-status/{id}")
    public ResponseEntity<ResponseData<String>> updateClinicStatus(
            @PathVariable UUID id,
            @RequestBody ClinicRequest request
    ) {
        ResponseData<String> response = iClinicService.updateClinicStatus(id, request.getStatus());
        return ResponseEntity.ok(response);
    }

}
