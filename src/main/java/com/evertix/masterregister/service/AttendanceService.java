package com.evertix.masterregister.service;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.model.request.AttendanceRequest;
import org.springframework.http.ResponseEntity;

public interface AttendanceService {
    ResponseEntity<MessageResponse> getAllAttendances(String status, Long managerId);
    ResponseEntity<MessageResponse> getAllAttendancesByEmployee(String status, Long employeeId);
    ResponseEntity<MessageResponse> markAttend(Long employeeId, AttendanceRequest attendance);
}
