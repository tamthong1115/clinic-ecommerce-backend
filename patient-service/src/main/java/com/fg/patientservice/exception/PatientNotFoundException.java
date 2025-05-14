package com.fg.patientservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PatientNotFoundException extends RuntimeException{
    private String message;

    public PatientNotFoundException(String message) {}
}
