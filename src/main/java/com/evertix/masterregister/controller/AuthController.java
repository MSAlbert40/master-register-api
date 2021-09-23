package com.evertix.masterregister.controller;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.controller.constants.ResponseConstants;
import com.evertix.masterregister.security.request.LoginRequest;
import com.evertix.masterregister.security.request.SignUpRequest;
import com.evertix.masterregister.security.response.JwtResponse;
import com.evertix.masterregister.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@Tag(name = "Authentication", description = "API is Ready")
@RequestMapping("api/auth")
@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "User Registration", description = "Registration for user", tags = {"Authentication"})
    public ResponseEntity<MessageResponse> signUpUser(@RequestBody @Valid SignUpRequest signUpRequest,
                                                      @RequestParam Long scheduleId,
                                                      @RequestParam Long workAreaId,
                                                      @RequestParam(required = false) @Parameter(description = "is Optional") Long managerId) {
        return this.authService.registerUser(signUpRequest, scheduleId, workAreaId, managerId);
    }

    @PostMapping("/login")
    @Operation(summary = "User Log in", description = "Log in for user. Returns JWT and user info", tags = {"Authentication"})
    public ResponseEntity<MessageResponse> LoginUser(@RequestBody @Valid LoginRequest loginRequest){
        MessageResponse msResponse;
        try {
            JwtResponse jwtResponse = this.authService.authenticationUser(loginRequest);
            msResponse = MessageResponse.builder().code(ResponseConstants.SUCCESS_CODE).message("Was Successfully Authentication").data(jwtResponse).build();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(msResponse);
        } catch (BadCredentialsException e) {
            msResponse = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message("The credentials are not correct").build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msResponse);
        } catch (Exception e) {
            msResponse = MessageResponse.builder().code(ResponseConstants.ERROR_CODE).message(e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msResponse);
        }
    }

    @PutMapping("/")
    @Operation(summary = "Change Password", description = "Change Password", tags = {"Authentication"})
    public ResponseEntity<MessageResponse> password(@RequestBody @Valid LoginRequest loginRequest) {
        return this.authService.updatePassword(loginRequest);
    }
}
