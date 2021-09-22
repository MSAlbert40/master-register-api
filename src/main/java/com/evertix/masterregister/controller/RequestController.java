package com.evertix.masterregister.controller;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.model.request.RequestRequest;
import com.evertix.masterregister.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@Tag(name = "Request", description = "API is Ready")
@RequestMapping("api/request")
@RestController
public class RequestController {
    @Autowired
    RequestService requestService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add Request", description = "Add Request",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Request"})
    public ResponseEntity<MessageResponse> addRequest(@RequestParam Long employeeId, @RequestParam Long typeRequestId,
                                                      @RequestBody @Valid RequestRequest request){
        return this.requestService.addRequest(employeeId, typeRequestId, request);
    }

    @GetMapping("/all/employee")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Request by Employee", description = "View All Request by Employee",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Request"})
    public ResponseEntity<MessageResponse> getAllRequestByEmployee(@RequestParam Long employeeId) {
        return this.requestService.getAllRequestByEmployee(employeeId);
    }

    @GetMapping("/employee")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Request by Employee and Type Request", description = "View All Request by Employee and Type Request",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Request"})
    public ResponseEntity<MessageResponse> getAllRequestByEmployeeAndTypeRequest(@RequestParam Long employeeId,
                                                                                 @RequestParam Long typeRequestId){
        return this.requestService.getAllRequestByEmployeeAndTypeRequest(employeeId, typeRequestId);
    }

    @GetMapping("/all/manager")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Request by Manager", description = "View All Request by Manager",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Request"})
    public ResponseEntity<MessageResponse> getAllRequestByManager(@RequestParam Long managerId){
        return this.requestService.getAllRequestByManager(managerId);
    }

    @GetMapping("/manager")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Request by Manager and Type Request", description = "View All Request by Manager and Type Request",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"Request"})
    public ResponseEntity<MessageResponse> getAllRequestByManagerAndTypeRequest(@RequestParam Long managerId,
                                                                                @RequestParam Long typeRequestId){
        return this.requestService.getAllRequestByManagerAndTypeRequest(managerId, typeRequestId);
    }
}
