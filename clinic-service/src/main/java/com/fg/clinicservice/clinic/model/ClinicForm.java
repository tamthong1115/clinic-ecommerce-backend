package com.fg.clinicservice.clinic.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ClinicForm {
    private String clinicName;
    private String clinicAddress;
    private String clinicPhone;
    private String description;
    private Clinic.Status status;
    private List<String> image;
    private List<MultipartFile> file;

}
