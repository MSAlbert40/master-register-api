package com.evertix.masterregister.controller;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.model.request.AttendanceRequest;
import com.evertix.masterregister.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@Tag(name = "Attendance", description = "API is Ready")
@RequestMapping("api/attendance")
@RestController
public class AttendanceController {
    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Attendances by Status and Manager", description = "View All Attendances by Status and Manager",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Attendance"})
    public ResponseEntity<MessageResponse> getAllAttendances(@RequestParam Long managerId,
                                                             @RequestParam String status){
        return this.attendanceService.getAllAttendances(status, managerId);
    }

    @GetMapping("/employee")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Attendances by Status and Employee", description = "View All Attendances by Status and Employee",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Attendance"})
    public ResponseEntity<MessageResponse> getAllAttendancesByEmployee(@RequestParam Long employeeId,
                                                                       @RequestParam String status){
        return this.attendanceService.getAllAttendancesByEmployee(status, employeeId);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add Attendance", description = "Add Attendance",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Attendance"})
    public ResponseEntity<MessageResponse> markAttend(@RequestParam Long employeeId,
                                                      @RequestBody @Valid AttendanceRequest attendanceRequest){
        return this.attendanceService.markAttend(employeeId, attendanceRequest);
    }
}
