package com.fg.clinicservice.controller;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementDto;
import com.fg.clinicservice.special_requirement.model.SpecialRequirementForm;
import com.fg.clinicservice.special_requirement.service.SpecialRequirementService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/service/sr")
public class SpecialRequirementController {
    private SpecialRequirementService specialRequirementService;

    public SpecialRequirementController(SpecialRequirementService specialRequirementService) {
        this.specialRequirementService = specialRequirementService;
    }

    @Operation(summary = "Add new special requirement for service")
    @PostMapping("/add-new")
    public ResponseEntity<ResponseData<SpecialRequirementDto>> addNewSpecialRequirement(
            @RequestBody SpecialRequirementForm specialRequirementForm) {
        ResponseData<SpecialRequirementDto> response = specialRequirementService.createNewSpecialRequirement(specialRequirementForm);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update special requirement")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<SpecialRequirementDto>> updateSpecialRequirement(
            @PathVariable UUID id, @RequestBody SpecialRequirementForm form) {
        ResponseData<SpecialRequirementDto> response = specialRequirementService.updateSpecialRequirement(id, form);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all by service")
    @GetMapping("/get-by-service/{id}")
    public ResponseEntity<ResponseData<List<SpecialRequirementDto>>> getSpecialRequirementByServiceId(
            @PathVariable UUID id) {
        ResponseData<List<SpecialRequirementDto>> response = specialRequirementService.findByServiceId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get by id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<SpecialRequirementDto>> getSpecialRequirementById(
            @PathVariable UUID id) {
        ResponseData<SpecialRequirementDto> response = specialRequirementService.findBySRId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove special request for serviceId")
    @DeleteMapping("/del/{id}")
    public ResponseEntity<ResponseData<String>> deleteSpecialRequirement(@PathVariable UUID id) {
        ResponseData<String> response = specialRequirementService.deleteByServiceId(id);
        return ResponseEntity.ok(response);
    }
}
