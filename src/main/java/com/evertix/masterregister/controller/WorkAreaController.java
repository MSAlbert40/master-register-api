package com.evertix.masterregister.controller;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.service.WorkAreaService;
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
@Tag(name = "Work Area", description = "API is Ready")
@RequestMapping("api/workArea")
@RestController
public class WorkAreaController {
    @Autowired
    WorkAreaService workAreaService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Work Area", description = "View All Work Area",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Work Area"})
    public ResponseEntity<MessageResponse> getAll() {
        return this.workAreaService.getAllWorkArea();
    }
}
