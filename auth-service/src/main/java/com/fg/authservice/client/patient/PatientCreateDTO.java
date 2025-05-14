package com.fg.authservice.client.patient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateDTO {
    @NotNull
    private UUID userId;
    
    private String firstName;
    
    private String lastName;
    
    @Email
    private String email;
    
    private LocalDate dateOfBirth;
    
    private String phone;
    
    private String address;
}