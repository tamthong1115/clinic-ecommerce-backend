package com.fg.clinicservice.clinic;



import com.fg.clinicservice.clinic.model.ClinicDto;
import com.fg.clinicservice.clinic.model.ClinicForm;
import com.fg.clinicservice.clinic.service.ClinicServiceImpl;
import com.fg.clinicservice.clinic.service.IClinicService;
import com.fg.clinicservice.clinic_service.model.ClinicService;
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

}
