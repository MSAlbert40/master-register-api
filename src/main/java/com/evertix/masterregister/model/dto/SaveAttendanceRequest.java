package com.evertix.masterregister.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class SaveAttendanceRequest {
    private LocalDate date;
    private LocalTime time;
    private Integer numHours;
    private Integer numDays;
    private String status;
    private String manager;
}
