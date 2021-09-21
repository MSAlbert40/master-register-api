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

    @PutMapping("/{managerId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update User", description = "Update User",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid SignUpRequest signUpRequest,
                                                  @PathVariable Long managerId) {
        return this.userService.updateUser(managerId, signUpRequest);
    }

    @PutMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Change Password", description = "Change Password",
            security = @SecurityRequirement(name = "bearerAuth"), tags = {"User"})
    public ResponseEntity<MessageResponse> password(@RequestBody @Valid LoginRequest loginRequest) {
        return this.userService.updatePassword(loginRequest);
    }
}
