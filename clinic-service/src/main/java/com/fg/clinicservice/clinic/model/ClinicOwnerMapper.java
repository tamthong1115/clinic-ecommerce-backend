package com.fg.clinicservice.clinic.model;

import com.fg.clinicservice.clinic.dto.ClinicOwnerDTO;
import com.fg.clinicservice.clinic.dto.CreateClinicOwnerRequest;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ClinicOwnerMapper {

    public static ClinicOwner fromCreateRequest(UUID userId, CreateClinicOwnerRequest request) {
        ClinicOwner owner = new ClinicOwner();
        owner.setUserId(userId);
        owner.setFirstName(request.getFirstName());
        owner.setLastName(request.getLastName());
        owner.setEmail(request.getEmail());
        owner.setPhoneNumber(request.getPhoneNumber());
        owner.setDateOfBirth(request.getDateOfBirth());
        owner.setAddress(request.getAddress());
        owner.setCity(request.getCity());
        owner.setState(request.getState());
        owner.setPostalCode(request.getPostalCode());
        owner.setProfileImageUrl(request.getProfileImageUrl());
        return owner;
    }

    public static ClinicOwnerDTO toDto(ClinicOwner owner) {
        return ClinicOwnerDTO.builder()
                .ownerId(owner.getOwnerId())
                .userId(owner.getUserId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .email(owner.getEmail())
                .phoneNumber(owner.getPhoneNumber())
                .dateOfBirth(owner.getDateOfBirth())
                .address(owner.getAddress())
                .city(owner.getCity())
                .state(owner.getState())
                .postalCode(owner.getPostalCode())
                .licenseNumber(owner.getLicenseNumber())
                .profileImageUrl(owner.getProfileImageUrl())
                .build();
    }
}