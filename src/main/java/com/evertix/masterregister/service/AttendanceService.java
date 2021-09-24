package com.evertix.masterregister.service;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.model.request.AttendanceRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface AttendanceService {
    ResponseEntity<MessageResponse> getAllAttendances(Long managerId);
    ResponseEntity<MessageResponse> getAllAttendancesByDate(LocalDate date);
    ResponseEntity<MessageResponse> getAllAttendancesByEmployee(Long employeeId);
    ResponseEntity<MessageResponse> markAttend(Long employeeId, AttendanceRequest attendance);
}
