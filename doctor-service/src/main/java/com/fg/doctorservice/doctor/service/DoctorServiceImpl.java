package com.fg.doctorservice.doctor.service;

import com.fg.doctorservice.client.user.AuthClient;
import com.fg.doctorservice.client.user.UserDTO;
import com.fg.doctorservice.doctor.DoctorMapper;
import com.fg.doctorservice.doctor.dto.DoctorRequest;
import com.fg.doctorservice.doctor.dto.DoctorBasicResponse;
import com.fg.doctorservice.doctor.dto.DoctorDetailResponse;
import com.fg.doctorservice.doctor.model.Doctor;
import com.fg.doctorservice.doctor.model.DoctorClinic;
import com.fg.doctorservice.doctor.repository.DoctorClinicRepository;
import com.fg.doctorservice.doctor.repository.DoctorRepository;
import com.fg.doctorservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorClinicRepository doctorClinicRepository;
    private final DoctorMapper doctorMapper;
    private final AuthClient authClient;

    @Override
    @Transactional
    public DoctorDetailResponse createDoctor(UUID ClinicId, DoctorRequest doctorRequest) {
        Doctor doctor = doctorMapper.toEntity(doctorRequest);
        Doctor savedDoctor = doctorRepository.save(doctor);

        if (ClinicId != null) {
            DoctorClinic doctorClinic = new DoctorClinic();
            doctorClinic.setDoctor(savedDoctor);
            doctorClinic.setClinicId(ClinicId);
            doctorClinicRepository.save(doctorClinic);
        }

        return doctorMapper.toDetailResponse(savedDoctor);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDetailResponse getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return doctorMapper.toDetailResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorBasicResponse> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toBasicResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DoctorDetailResponse updateDoctor(UUID id, DoctorRequest doctorRequest) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        
        doctorMapper.updateEntity(doctor, doctorRequest);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDetailResponse(updatedDoctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(UUID id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    @Transactional()
    public UserDTO getCurrentUser() {
        return authClient.getCurrentUser().getBody();
    }

}
