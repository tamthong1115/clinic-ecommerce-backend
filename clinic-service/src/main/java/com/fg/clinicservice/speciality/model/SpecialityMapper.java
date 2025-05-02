package com.fg.clinicservice.speciality.model;

import org.springframework.stereotype.Component;

@Component
public class SpecialityMapper {
    public static SpecialityDto toDto(Speciality speciality) {
        if(speciality == null) return null;

        return SpecialityDto.builder()
                .specialityId(speciality.getSpecialityId())
                .specialityName(speciality.getSpecialityName())
                .specialityDescription(speciality.getSpecialityDescription())
                .specialityImageUrl(speciality.getSpecialityImageUrl())
                .build();
    }

    public static Speciality fromDto(SpecialityDto specialityDto) {
        if(specialityDto == null) return null;

        return Speciality.builder()
                .specialityId(specialityDto.getSpecialityId())
                .specialityName(specialityDto.getSpecialityName())
                .specialityDescription(specialityDto.getSpecialityDescription())
                .specialityImageUrl(specialityDto.getSpecialityImageUrl())
                .build();
    }

    public static void updateSpecialityForm(Speciality speciality, SpecialityForm specialityForm) {
        if(speciality == null || specialityForm == null)
            throw new IllegalArgumentException("Speciality or SpecialityForm cannot be null");

        if (specialityForm.getSpecialityName() != null) speciality.setSpecialityName(specialityForm.getSpecialityName());
        if(specialityForm.getSpecialityDescription() != null) speciality.setSpecialityDescription(specialityForm.getSpecialityDescription());
        if(specialityForm.getSpecialityImageUrl() != null) speciality.setSpecialityImageUrl(specialityForm.getSpecialityImageUrl());
    }

    public static Speciality fromForm(SpecialityForm specialityForm) {
        if(specialityForm == null)
            throw new IllegalArgumentException("SpecialityForm cannot be null");

        Speciality speciality = new Speciality();
        speciality.setSpecialityName(specialityForm.getSpecialityName());
        speciality.setSpecialityDescription(specialityForm.getSpecialityDescription());
        speciality.setSpecialityImageUrl(specialityForm.getSpecialityImageUrl());
        return speciality;
    }
}
