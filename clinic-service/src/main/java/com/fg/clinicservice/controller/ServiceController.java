package com.fg.clinicservice.controller;

import com.fg.clinicservice.response.ResponseData;
import com.fg.clinicservice.service.model.ServiceDto;
import com.fg.clinicservice.service.model.ServiceForm;
import com.fg.clinicservice.service.service.IService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/vi/service")
public class ServiceController {
    private IService iService;

    public ServiceController(IService iService) {
        this.iService = iService;
    }

    @Operation(summary = "Create new service")
    @PostMapping("/create")
    ResponseEntity<ResponseData<ServiceDto>> createNewService(@RequestBody ServiceForm serviceForm) {
        ResponseData<ServiceDto> responseData = iService.createService(serviceForm);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Update information for service")
    @PutMapping("/update/{id}")
    ResponseEntity<ResponseData<ServiceDto>> updateService(@PathVariable UUID id, @RequestBody ServiceForm serviceForm) {
        ResponseData<ServiceDto> responseData = iService.updateService(id, serviceForm);
        return ResponseEntity.ok(responseData);
    }

    @Operation(summary = "Get all service")
    @GetMapping("/get-all")
    ResponseEntity<ResponseData<List<ServiceDto>>> getAllServices() {
        ResponseData<List<ServiceDto>> listService = iService.getAllService();
        return ResponseEntity.ok(listService);
    }

    @Operation(summary = "Get service by ID")
    @GetMapping("/{id}")
    ResponseEntity<ResponseData<ServiceDto>> getServiceById(@PathVariable UUID id) {
        ResponseData<ServiceDto> responseData = iService.getServiceById(id);
        return ResponseEntity.ok(responseData);
    }

}
