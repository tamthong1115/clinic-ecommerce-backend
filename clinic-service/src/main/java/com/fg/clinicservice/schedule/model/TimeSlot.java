package com.fg.clinicservice.schedule.model;


import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static List<TimeSlot> generateTimeSlots(
            LocalTime dayStart,
            LocalTime dayEnd,
            int slotDurationMinutes,
            int breakMinutes) {

        List<TimeSlot> slots = new ArrayList<>();
        LocalTime currentStart = dayStart;

        while (currentStart.plusMinutes(slotDurationMinutes).isBefore(dayEnd) ||
                currentStart.plusMinutes(slotDurationMinutes).equals(dayEnd)) {

            LocalTime currentEnd = currentStart.plusMinutes(slotDurationMinutes);
            slots.add(new TimeSlot(currentStart, currentEnd));
            currentStart = currentEnd.plusMinutes(breakMinutes);
        }

        return slots;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        return startTime.format(formatter) + " - " + endTime.format(formatter);
    }
}