package com.fg.doctorservice.doctor;

import com.fg.doctorservice.doctor.dto.DoctorScheduleRequest;
import com.fg.doctorservice.doctor.dto.DoctorScheduleDTO;
import com.fg.doctorservice.doctor.model.Doctor;
import com.fg.doctorservice.doctor.model.DoctorSchedule;
import com.fg.doctorservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    @Transactional
    public DoctorScheduleDTO createSchedule(UUID doctorId, DoctorScheduleRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setId(UUID.randomUUID());
        schedule.setDoctor(doctor);
        schedule.setClinicId(request.getClinicId());
        schedule.setDay_of_week(request.getDayOfWeek());
        schedule.setStart_time(request.getStartTime());
        schedule.setEnd_time(request.getEndTime());

        DoctorSchedule savedSchedule = scheduleRepository.save(schedule);
        return doctorMapper.toScheduleDTO(savedSchedule);
    }

    @Override
    @Transactional
    public DoctorScheduleDTO updateSchedule(UUID scheduleId, DoctorScheduleRequest request) {
        DoctorSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + scheduleId));

        schedule.setClinicId(request.getClinicId());
        schedule.setDay_of_week(request.getDayOfWeek());
        schedule.setStart_time(request.getStartTime());
        schedule.setEnd_time(request.getEndTime());

        DoctorSchedule updatedSchedule = scheduleRepository.save(schedule);
        return doctorMapper.toScheduleDTO(updatedSchedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(UUID scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new ResourceNotFoundException("Schedule not found with id: " + scheduleId);
        }
        scheduleRepository.deleteById(scheduleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorScheduleDTO> getDoctorSchedules(UUID doctorId) {
        return scheduleRepository.findByDoctorId(doctorId).stream()
                .map(doctorMapper::toScheduleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorScheduleDTO> getClinicSchedules(UUID clinicId) {
        return scheduleRepository.findByClinicId(clinicId).stream()
                .map(doctorMapper::toScheduleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorScheduleDTO getScheduleById(UUID scheduleId) {
        DoctorSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + scheduleId));
        return doctorMapper.toScheduleDTO(schedule);
    }
} 