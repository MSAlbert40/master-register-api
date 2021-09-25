package com.evertix.masterregister.controller;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.service.TypeRequestService;
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
@Tag(name = "Type Request", description = "API is Ready")
@RequestMapping("api/typeRequest")
@RestController
public class TypeRequestController {
    @Autowired
    TypeRequestService typeRequestService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Type Request", description = "View All Type Request",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Type Request"})
    public ResponseEntity<MessageResponse> getAll() {
        return this.typeRequestService.getAllTypeRequest();
    }
}
