package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.dto.DoctorBasicResponse;
import com.fg.doctorservice.doctor.dto.DoctorDetailResponse;
import com.fg.doctorservice.doctor.dto.DoctorRequest;
import com.fg.doctorservice.doctor.dto.DoctorCertificationDTO;
import com.fg.doctorservice.schedule.DoctorScheduleDTO;
import com.fg.doctorservice.doctor.model.Doctor;
import com.fg.doctorservice.doctor.model.DoctorCertification;
import com.fg.doctorservice.schedule.DoctorSchedule;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DoctorMapper {

    public DoctorBasicResponse toBasicResponse(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorBasicResponse response = new DoctorBasicResponse();
        response.setId(doctor.getId());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setEmail(doctor.getEmail());
        response.setPhone(doctor.getPhone());
        response.setGender(doctor.getGender());
        response.setProfilePicture(doctor.getProfilePicture());

        return response;
    }

    public DoctorDetailResponse toDetailResponse(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDetailResponse response = new DoctorDetailResponse();
        response.setId(doctor.getId());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setEmail(doctor.getEmail());
        response.setPhone(doctor.getPhone());
        response.setGender(doctor.getGender());
        response.setProfilePicture(doctor.getProfilePicture());
        response.setExperienceYears(doctor.getExperienceYears());
        response.setEducation(doctor.getEducation());

        if (doctor.getDoctorCertifications() != null) {
            response.setCertifications(doctor.getDoctorCertifications().stream()
                    .map(this::toCertificationDTO)
                    .collect(Collectors.toList()));
        }

        if (doctor.getDoctorSchedules() != null) {
            response.setSchedules(doctor.getDoctorSchedules().stream()
                    .map(this::toScheduleDTO)
                    .collect(Collectors.toList()));
        }

        if (doctor.getDoctorClinics() != null) {
            response.setClinicIds(doctor.getDoctorClinics().stream()
                    .map(dc -> dc.getClinicId())
                    .collect(Collectors.toList()));
        }

        return response;
    }

    public Doctor toEntity(DoctorRequest request) {
        if (request == null) {
            return null;
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setGender(request.getGender());
        doctor.setProfilePicture(request.getProfilePicture());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setEducation(request.getEducation());

        return doctor;
    }

    private DoctorCertificationDTO toCertificationDTO(DoctorCertification certification) {
        if (certification == null) {
            return null;
        }

        DoctorCertificationDTO dto = new DoctorCertificationDTO();
        dto.setId(certification.getId());
        dto.setCertificationName(certification.getCertificationName());
        dto.setCertificationNumber(certification.getCertificationNumber());
        dto.setSpecialty(certification.getSpecialty());
        dto.setIssuedBy(certification.getIssuedBy());
        dto.setStatus(certification.getStatus());
        dto.setCertificateImageUrl(certification.getCertificateImageUrl());
        dto.setIssueDate(certification.getIssueDate());
        dto.setExpiryDate(certification.getExpiryDate());

        return dto;
    }

    public DoctorScheduleDTO toScheduleDTO(DoctorSchedule schedule) {
        if (schedule == null) {
            return null;
        }

        DoctorScheduleDTO dto = new DoctorScheduleDTO();
        dto.setId(schedule.getId());
        dto.setClinicId(schedule.getClinicId());
        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());

        return dto;
    }

    public void updateEntity(Doctor doctor, DoctorRequest request) {
        if (doctor == null || request == null) {
            return;
        }

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setGender(request.getGender());
        doctor.setProfilePicture(request.getProfilePicture());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setEducation(request.getEducation());
    }
}
