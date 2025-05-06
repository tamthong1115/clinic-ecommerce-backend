package com.fg.clinicservice.doctor.service;


import com.fg.clinicservice.client.user.UserDTO;
import com.fg.clinicservice.doctor.dto.DoctorBasicResponse;
import com.fg.clinicservice.doctor.dto.DoctorDetailResponse;
import com.fg.clinicservice.doctor.dto.DoctorRequest;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    DoctorDetailResponse createDoctor(UUID clinicId, DoctorRequest doctorRequest);
    DoctorDetailResponse getDoctorById(UUID id);
    List<DoctorBasicResponse> getAllDoctors();
    DoctorDetailResponse updateDoctor(UUID id, DoctorRequest doctorRequest);
    void deleteDoctor(UUID id);
    UserDTO getCurrentUser();

}
