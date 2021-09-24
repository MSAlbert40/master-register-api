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
    private Integer attendance;
    private Integer late;
    private Integer absent;
    private Integer quantity;
    private String status;
    private String manager;
}
