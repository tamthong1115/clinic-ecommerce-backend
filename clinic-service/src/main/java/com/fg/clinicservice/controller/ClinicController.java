package com.fg.clinicservice.controller;



import com.fg.clinicservice.clinic.model.Clinic;
import com.fg.clinicservice.clinic.model.ClinicDto;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.clinic.model.ClinicRequest;
import com.fg.clinicservice.clinic.service.IClinicService;
import com.fg.clinicservice.config.feign.AuthClient;
import com.fg.clinicservice.response.ResponseData;
import com.fg.common.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.ws.rs.ForbiddenException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {

    private IClinicService iClinicService;
    private AuthClient authClient;

    public ClinicController(IClinicService iClinicService) {
        this.iClinicService = iClinicService;
    }

    @Operation(summary = "Create new clinic for owner")
    @PostMapping("/create")
    public ResponseEntity<ResponseData<ClinicDto>> CreateClinic(@RequestBody ClinicForm clinicForm) {
        ResponseData<ClinicDto> response = iClinicService.createNewClinic(clinicForm);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Update information for clinic")
    @PostMapping("/update/{id}")
    public ResponseEntity<ResponseData<ClinicDto>> updateClinic(@PathVariable UUID id, @RequestBody ClinicForm clinicForm) {
        ResponseData<ClinicDto> response = iClinicService.updateClinic(id,clinicForm);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update status of clinic")
    @PatchMapping("/set-status/{id}")
    public ResponseEntity<ResponseData<String>> updateClinicStatus(
            @RequestHeader("Authorization") String token,
            @PathVariable UUID id,
            @RequestBody ClinicRequest request
    ) {
        UserDto user = authClient.validateToken(token).getBody();
        if (!authClient.hasRole(user.getId(), "USER")) {
            throw new ForbiddenException();
        }
        ResponseData<String> response = iClinicService.updateClinicStatus(id, request.getStatus());
        return ResponseEntity.ok(response);
    }

}
