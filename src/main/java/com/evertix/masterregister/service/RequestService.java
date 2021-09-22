package com.evertix.masterregister.service;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.model.request.RequestRequest;
import org.springframework.http.ResponseEntity;

public interface RequestService {
    ResponseEntity<MessageResponse> addRequest(Long employeeId, Long typeRequestId, RequestRequest request);
    ResponseEntity<MessageResponse> getAllRequestByEmployee(Long employeeId);
    ResponseEntity<MessageResponse> getAllRequestByEmployeeAndTypeRequest(Long employeeId, Long typeRequest);
    ResponseEntity<MessageResponse> getAllRequestByManager(Long managerId);
    ResponseEntity<MessageResponse> getAllRequestByManagerAndTypeRequest(Long managerId, Long typeRequest);
}
