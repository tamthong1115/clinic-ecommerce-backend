package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.dto.DoctorRequest;
import com.fg.doctorservice.doctor.dto.DoctorBasicResponse;
import com.fg.doctorservice.doctor.dto.DoctorDetailResponse;
import com.fg.doctorservice.doctor.model.Doctor;
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
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional
    public DoctorDetailResponse createDoctor(DoctorRequest doctorRequest) {
        Doctor doctor = doctorMapper.toEntity(doctorRequest);
        doctor.setId(UUID.randomUUID());
        Doctor savedDoctor = doctorRepository.save(doctor);
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
    @Transactional(readOnly = true)
    public List<DoctorBasicResponse> getDoctorsByGender(String gender) {
        return doctorRepository.findByGender(gender).stream()
                .map(doctorMapper::toBasicResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorBasicResponse> getDoctorsByExperienceYears(Integer experienceYears) {
        return doctorRepository.findByExperienceYearsGreaterThanEqual(experienceYears).stream()
                .map(doctorMapper::toBasicResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorBasicResponse> getDoctorsByEducation(String education) {
        return doctorRepository.findByEducationContaining(education).stream()
                .map(doctorMapper::toBasicResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDetailResponse getDoctorByEmail(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with email: " + email));
        return doctorMapper.toDetailResponse(doctor);
    }
}
