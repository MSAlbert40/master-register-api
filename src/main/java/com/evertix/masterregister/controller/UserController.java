package com.evertix.masterregister.controller;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.security.request.LoginRequest;
import com.evertix.masterregister.security.request.SignUpRequest;
import com.evertix.masterregister.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@Tag(name = "User", description = "API is Ready")
@RequestMapping("api/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View All Employees by Manager", description = "View All Employees by Manager",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<MessageResponse> getAllByManager(@RequestParam Long managerId) {
        return this.userService.getAllEmployeeByManager(managerId);
    }

    @GetMapping("/employee")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View Employee by Manager", description = "View Employee by Manager",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<MessageResponse> getEmployee(@RequestParam Long employeeId) {
        return this.userService.getEmployeeByManager(employeeId);
    }

    @GetMapping("/{name}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View Employee by Name", description = "View Employee by Name",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<MessageResponse> getAllEmployeeByName(@RequestParam Long managerId,
                                                                @PathVariable String name){
        return this.userService.getAllEmployeeByName(managerId, name);
    }

    @GetMapping("/workArea")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "View Employee by Work Area", description = "View Employee by Work Area",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<MessageResponse> getAllEmployeeByWorkArea(@RequestParam Long managerId,
                                                                    @RequestParam Long workAreaId){
        return this.userService.getAllEmployeeByWorkArea(managerId, workAreaId);
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update Employee or Manager", description = "Update Employee or Manager",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid SignUpRequest signUpRequest,
                                                  @PathVariable Long employeeId) {
        return this.userService.updateUser(employeeId, signUpRequest);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete Employee", description = "Delete Employee",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<MessageResponse> delete(@RequestParam Long managerId,
                                                  @RequestParam Long employeeId){
        return this.userService.deleteEmployee(managerId, employeeId);
    }
}
