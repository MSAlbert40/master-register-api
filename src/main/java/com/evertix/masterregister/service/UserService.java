package com.evertix.masterregister.service;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.security.request.LoginRequest;
import com.evertix.masterregister.security.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<MessageResponse> getAllEmployeeByManager(Long id);
    ResponseEntity<MessageResponse> getEmployeeByManager(Long employeeId);
    ResponseEntity<MessageResponse> updatePassword(LoginRequest loginRequest);
    ResponseEntity<MessageResponse> updateUser(Long id, SignUpRequest signUpRequest);
}
