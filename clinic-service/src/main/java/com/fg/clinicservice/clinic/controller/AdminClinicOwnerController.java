package com.fg.clinicservice.clinic.controller;

import com.fg.clinicservice.clinic.dto.ClinicOwnerDTO;
import com.fg.clinicservice.clinic.dto.CreateClinicOwnerRequest;
import com.fg.clinicservice.clinic.service.IClinicService;
import com.fg.clinicservice.clinic_service.model.ClinicService;
import com.fg.clinicservice.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/clinic-owners")
public class AdminClinicOwnerController {
    private final IClinicService clinicService;

    public AdminClinicOwnerController(IClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping
    public ResponseData<ClinicOwnerDTO> createClinicOwner(@RequestBody CreateClinicOwnerRequest request) {
        return clinicService.createClinicOwner(request);
    }
}
