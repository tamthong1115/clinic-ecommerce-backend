package com.fg.patientservice.patient.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "patient")
public class Patient {
    @Id
    @Column(name="id",unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID patientId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private String email;

    private String phone;

    @Column(name="date_of_birth")
    private String dateOfBirth;

    private String gender;

    @Column(name="address_line")
    private String addressLine;

}
