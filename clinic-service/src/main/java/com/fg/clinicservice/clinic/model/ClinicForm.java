package com.fg.clinicservice.clinic.model;

import lombok.Data;

@Data
public class ClinicForm {
    private String clinicName;
    private String clinicAddress;
    private String clinicPhone;
    private String description;
    private String image;
    private Clinic.Status status;
}
