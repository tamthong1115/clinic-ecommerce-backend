package com.fg.appointment_service.doctor.service;

import com.fg.appointment_service.client.user.UserDTO;
import com.fg.appointment_service.doctor.dto.DoctorRequest;
import com.fg.appointment_service.doctor.dto.DoctorBasicResponse;
import com.fg.appointment_service.doctor.dto.DoctorDetailResponse;
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
