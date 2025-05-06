package com.fg.clinicservice.clinic.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.UUID;

@Data
public class ClinicForm {
    private String clinicName;
    private UUID ownerId;
    private String clinicAddress;
    private String clinicPhone;
    private String description;
    private Clinic.Status status;
    private List<String> image;
    private List<MultipartFile> file;

}
