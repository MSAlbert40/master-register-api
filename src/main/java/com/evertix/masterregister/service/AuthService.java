package com.evertix.masterregister.service;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.security.request.LoginRequest;
import com.evertix.masterregister.security.request.SignUpRequest;
import com.evertix.masterregister.security.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<MessageResponse> registerUser(SignUpRequest signUpRequest, Long scheduleId, Long workAreaId, Long managerId);
    ResponseEntity<MessageResponse> updatePassword(LoginRequest loginRequest);
    JwtResponse authenticationUser(LoginRequest loginRequest);
}
