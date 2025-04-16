package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.dto.DoctorRequest;
import com.fg.doctorservice.doctor.dto.DoctorBasicResponse;
import com.fg.doctorservice.doctor.dto.DoctorDetailResponse;
import java.util.List;
import java.util.UUID;

public interface DoctorService {
    DoctorDetailResponse createDoctor(DoctorRequest doctorRequest);
    DoctorDetailResponse getDoctorById(UUID id);
    List<DoctorBasicResponse> getAllDoctors();
    DoctorDetailResponse updateDoctor(UUID id, DoctorRequest doctorRequest);
    void deleteDoctor(UUID id);
    List<DoctorBasicResponse> getDoctorsByGender(String gender);
    List<DoctorBasicResponse> getDoctorsByExperienceYears(Integer experienceYears);
    List<DoctorBasicResponse> getDoctorsByEducation(String education);
    DoctorDetailResponse getDoctorByEmail(String email);
}
