package com.fg.appointment.service;

import com.fg.appointment.client.clinic.ClinicClient;
import com.fg.appointment.client.clinic.DoctorIdResponse;
import com.fg.appointment.dto.AppointmentDTO;
import com.fg.appointment.dto.AppointmentCreateDTO;
import com.fg.appointment.dto.TimeSlotDTO;
import com.fg.appointment.exception.OperationFailedException;
import com.fg.appointment.exception.ResourceNotFoundException;
import com.fg.appointment.model.Appointment;
import com.fg.appointment.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClinicClient clinicClient;

    @Override
    public AppointmentDTO createAppointment(AppointmentCreateDTO dto) {
        try {
            Appointment appointment = new Appointment();
            appointment.setDoctorId(dto.getDoctorId());
            appointment.setPatientId(dto.getPatientId());
            appointment.setClinicId(dto.getClinicId());
            appointment.setAppointmentDate(dto.getAppointmentDate());
            appointment.setStartTime(dto.getStartTime());
            appointment.setEndTime(dto.getEndTime());
            appointment.setStatus(dto.getStatus());


            appointment = appointmentRepository.save(appointment);
            return toDTO(appointment);
        } catch (Exception e) {
            throw new OperationFailedException("Failed to create appointment: " + e.getMessage());
        }
    }

    @Override
    public Optional<AppointmentDTO> getAppointmentById(UUID id) {
        return appointmentRepository.findById(id).map(this::toDTO);
    }

    @Override
    public AppointmentDTO updateAppointment(UUID id, AppointmentCreateDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));
        try {
            appointment.setDoctorId(dto.getDoctorId());
            appointment.setPatientId(dto.getPatientId());
            appointment.setClinicId(dto.getClinicId());
            appointment.setAppointmentDate(dto.getAppointmentDate());
            appointment.setStartTime(dto.getStartTime());
            appointment.setEndTime(dto.getEndTime());
            appointment.setStatus(dto.getStatus());
            appointment = appointmentRepository.save(appointment);
            return toDTO(appointment);
        } catch (Exception e) {
            throw new OperationFailedException("Failed to update appointment: " + e.getMessage());
        }
    }

    @Override
    public void deleteAppointment(UUID id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found");
        }
        try {
            appointmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new OperationFailedException("Failed to delete appointment: " + e.getMessage());
        }
    }


    public List<TimeSlotDTO> getBookedTimeSlots(UUID doctorId, LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
        try {
            return appointments.stream().map(a -> {
                TimeSlotDTO dto = new TimeSlotDTO();
                dto.setStartTime(a.getStartTime());
                dto.setEndTime(a.getEndTime());
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new OperationFailedException("Failed to get booked time slots: " + e.getMessage());
        }
    }

    public List<AppointmentDTO> getAppointmentsByPatientId(UUID userId) {

        // Get patientId from userId using the client
        DoctorIdResponse response = clinicClient.getPatientIdByUserId(userId);

        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointments.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Map<LocalDate, List<AppointmentDTO>> getDoctorCalendarAppointments(UUID userId, LocalDate referenceDate) {

        // Get doctorId from userId using the client
        DoctorIdResponse response = clinicClient.getDoctorIdByUserId(userId);

        if (response == null || response.getDoctorId() == null) {
            throw new ResourceNotFoundException("Doctor not found for userId: " + userId);
        }

        UUID doctorId = response.getDoctorId();

        // Check if the doctor exists
        if (!appointmentRepository.existsByDoctorId(doctorId)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }

        // Calculate start and end dates (3 weeks before and after)
        LocalDate startDate = referenceDate.minusWeeks(3);
        LocalDate endDate = referenceDate.plusWeeks(3);

        List<Appointment> appointments = appointmentRepository
                .findByDoctorIdAndAppointmentDateBetween(doctorId, startDate, endDate);

        Map<LocalDate, List<AppointmentDTO>> calendarData = new HashMap<>();

        // Initialize all dates in the range with empty lists
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            calendarData.put(date, new ArrayList<>());
        }

        appointments.forEach(appointment -> {
            LocalDate appointmentDate = appointment.getAppointmentDate();
            if (!calendarData.containsKey(appointmentDate)) {
                calendarData.put(appointmentDate, new ArrayList<>());
            }
            calendarData.get(appointmentDate).add(toDTO(appointment));
        });

        return calendarData;
    }

    private AppointmentDTO toDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setPatientId(appointment.getPatientId());
        dto.setClinicId(appointment.getClinicId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStartTime(appointment.getStartTime());
        dto.setEndTime(appointment.getEndTime());
        dto.setStatus(appointment.getStatus());
        return dto;
    }


}