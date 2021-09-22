package com.evertix.masterregister.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRequest {
    private LocalDateTime dateTime;
}
