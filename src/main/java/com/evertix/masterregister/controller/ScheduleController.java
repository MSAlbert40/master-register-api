package com.evertix.masterregister.controller;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@Tag(name = "Schedule", description = "API is Ready")
@RequestMapping("api/schedule")
@RestController
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Schedule", description = "View All Schedule",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Schedule"})
    public ResponseEntity<MessageResponse> getAll() {
        return this.scheduleService.getAllSchedule();
    }
}
