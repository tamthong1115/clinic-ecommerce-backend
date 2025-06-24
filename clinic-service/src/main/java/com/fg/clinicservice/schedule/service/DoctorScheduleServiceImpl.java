package com.fg.clinicservice.schedule.service;

import com.fg.clinicservice.client.appointment.AppointmentClient;
import com.fg.clinicservice.client.appointment.TimeSlotDTO;
import com.fg.clinicservice.client.user.AuthClient;
import com.fg.clinicservice.client.user.UserDTO;
import com.fg.clinicservice.schedule.dto.DoctorScheduleDTO;
import com.fg.clinicservice.schedule.dto.DoctorScheduleRequest;
import com.fg.clinicservice.schedule.model.DoctorSchedule;
import com.fg.clinicservice.schedule.model.TimeSlot;
import com.fg.clinicservice.schedule.repository.DoctorScheduleRepository;
import com.fg.clinicservice.doctor.DoctorMapper;
import com.fg.clinicservice.doctor.model.Doctor;
import com.fg.clinicservice.doctor.repository.DoctorRepository;
import com.fg.clinicservice.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final AuthClient authClient;
    private final AppointmentClient appointmentClient;

    @Override
    @Transactional
    public DoctorScheduleDTO createScheduleByUserId(UUID userId,UUID doctorId, DoctorScheduleRequest request) {
        Doctor doctor = null;
        if(doctorId == null){
           doctor = doctorRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found for user ID: " + userId));
        }else{
            doctor = doctorRepository.findById(doctorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));
        }

        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setClinicId(doctor.getClinic().getClinicId());


        DoctorSchedule savedSchedule = doctorScheduleRepository.save(schedule);
        return doctorMapper.toScheduleDTO(savedSchedule);
    }

    @Override
    @Transactional
    public DoctorScheduleDTO updateSchedule(UUID clinicId, UUID scheduleId, DoctorScheduleRequest request) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + scheduleId));

        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());

        DoctorSchedule updatedSchedule = doctorScheduleRepository.save(schedule);
        return doctorMapper.toScheduleDTO(updatedSchedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(UUID scheduleId) {
        if (!doctorScheduleRepository.existsById(scheduleId)) {
            throw new ResourceNotFoundException("Schedule not found with id: " + scheduleId);
        }
        doctorScheduleRepository.deleteById(scheduleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorScheduleDTO> getDoctorSchedules() {
        //Get current authenticated user
        UserDTO user = authClient.getCurrentUser().getBody();

        // Get doctor by userId
        Doctor doctor = doctorRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with userId: " + user.getUserId()));
        UUID doctorId = doctor.getId();

        return doctorScheduleRepository.findByDoctorId(doctorId).stream()
                .map(doctorMapper::toScheduleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorScheduleDTO> getClinicSchedules(UUID clinicId) {
        return doctorScheduleRepository.findByClinicId(clinicId).stream()
                .map(doctorMapper::toScheduleDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorScheduleDTO getScheduleById(UUID scheduleId) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + scheduleId));
        return doctorMapper.toScheduleDTO(schedule);
    }



    public List<TimeSlot> getAvailableTimeSlots(UUID doctorId, LocalDate date) {
        // Get day of week from date
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // Get doctor's schedule for this day
        List<DoctorSchedule> schedules = doctorScheduleRepository
                .findByDoctorIdAndDayOfWeekAndIsActiveTrue(doctorId, dayOfWeek);

        // Get appointments already booked for this date and doctor
        List<TimeSlot> bookedSlots = getBookedTimeSlots(doctorId, date);

        // Generate all possible time slots from schedule
        List<TimeSlot> availableSlots = schedules.stream()
                .flatMap(schedule -> TimeSlot.generateTimeSlots(
                        schedule.getStartTime(),
                        schedule.getEndTime(),
                        schedule.getSlotDurationMinutes(),
                        schedule.getBreakMinutesBetweenSlots()
                ).stream())
                .collect(Collectors.toList());

        // Remove already booked slots
        availableSlots.removeIf(availableSlot ->
                bookedSlots.stream().anyMatch(bookedSlot ->
                        isOverlapping(availableSlot, bookedSlot)
                )
        );

        return availableSlots;
    }


    private boolean isOverlapping(TimeSlot slot1, TimeSlot slot2) {
        return slot1.getStartTime().isBefore(slot2.getEndTime()) &&
                slot2.getStartTime().isBefore(slot1.getEndTime());
    }

    private List<TimeSlot> getBookedTimeSlots(UUID doctorId, LocalDate date) {
        List<TimeSlotDTO> booked = appointmentClient.getBookedTimeSlots(doctorId, date);
        return booked.stream()
                .map(dto -> new TimeSlot(dto.getStartTime(), dto.getEndTime()))
                .collect(Collectors.toList());
    }

    public boolean isValidTimeSlot(UUID doctorId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<TimeSlot> availableSlots = getAvailableTimeSlots(doctorId, date);
        TimeSlot requestedSlot = new TimeSlot(startTime, endTime);

        return availableSlots.stream()
                .anyMatch(slot ->
                        slot.getStartTime().equals(requestedSlot.getStartTime()) &&
                                slot.getEndTime().equals(requestedSlot.getEndTime())
                );
    }
} 