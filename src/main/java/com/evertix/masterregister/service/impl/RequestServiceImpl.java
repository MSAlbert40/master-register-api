package com.evertix.masterregister.service.impl;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.controller.constants.ResponseConstants;
import com.evertix.masterregister.model.Request;
import com.evertix.masterregister.model.TypeRequest;
import com.evertix.masterregister.model.User;
import com.evertix.masterregister.model.dto.SaveRequestRequest;
import com.evertix.masterregister.model.request.RequestRequest;
import com.evertix.masterregister.repository.RequestRepository;
import com.evertix.masterregister.repository.TypeRequestRepository;
import com.evertix.masterregister.repository.UserRepository;
import com.evertix.masterregister.service.RequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TypeRequestRepository typeRequestRepository;

    @Autowired
    RequestRepository requestRepository;

    @Override
    public ResponseEntity<MessageResponse> addRequest(Long employeeId, Long typeRequestId, RequestRequest request) {
        try {
            // Valid if Employee Exists
            User employee = userRepository.findById(employeeId).orElse(null);
            if (employee == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists employee with ID: " + employeeId)
                                .build());
            }

            // Valid if Type Request Exists
            TypeRequest typeRequest = typeRequestRepository.findById(typeRequestId).orElse(null);
            if (typeRequest == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists Type Request with ID: " + typeRequestId)
                                .build());
            }

            // Valid Complete
            Request saveRequest = this.convertToEntity(request);
            // Set TypeRequest, Manager & Employee
            saveRequest.setTypeRequest(typeRequest);
            saveRequest.setEmployee(employee);
            // Save Request
            requestRepository.save(saveRequest);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Successful update")
                            .data(this.convertToResource(saveRequest))
                            .build());
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.ERROR_CODE)
                            .message("Internal Error: " + sw.toString())
                            .build());
        }
    }

    @Override
    public ResponseEntity<MessageResponse> getAllRequestByEmployee(Long employeeId) {
        try {
            List<Request> requestList = this.requestRepository.findAllByEmployeeId(employeeId);
            if (requestList == null || requestList.isEmpty()) { return this.getNotRequestContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(requestList)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.ERROR_CODE)
                            .message("Internal Error: " + sw.toString())
                            .build());
        }
    }

    @Override
    public ResponseEntity<MessageResponse> getAllRequestByEmployeeAndTypeRequest(Long employeeId, Long typeRequest) {
        try {
            List<Request> requestList = this.requestRepository.findAllByEmployeeIdAndTypeRequestId(employeeId, typeRequest);
            if (requestList == null || requestList.isEmpty()) { return this.getNotRequestContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(requestList)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.ERROR_CODE)
                            .message("Internal Error: " + sw.toString())
                            .build());
        }
    }

    @Override
    public ResponseEntity<MessageResponse> getAllRequestByManager(Long managerId) {
        try {
            List<Request> requestList = this.requestRepository.findAllByEmployeeManagerId(managerId);
            if (requestList == null || requestList.isEmpty()) { return this.getNotRequestContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(requestList)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.ERROR_CODE)
                            .message("Internal Error: " + sw.toString())
                            .build());
        }
    }

    @Override
    public ResponseEntity<MessageResponse> getAllRequestByManagerAndTypeRequest(Long managerId, Long typeRequest) {
        try {
            List<Request> requestList = this.requestRepository.findAllByEmployeeManagerIdAndTypeRequestId(managerId, typeRequest);
            if (requestList == null || requestList.isEmpty()) { return this.getNotRequestContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(requestList)
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.ERROR_CODE)
                            .message("Internal Error: " + sw.toString())
                            .build());
        }
    }

    private Request convertToEntity(RequestRequest request) { return modelMapper.map(request, Request.class); }

    private SaveRequestRequest convertToResource(Request request) {
        SaveRequestRequest resource = modelMapper.map(request, SaveRequestRequest.class);
        resource.setTypeRequest(request.getTypeRequest().getName());
        return resource;
    }

    private ResponseEntity<MessageResponse> getNotRequestContent() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder()
                        .code(ResponseConstants.WARNING_CODE)
                        .message(ResponseConstants.MSG_WARNING_CONS)
                        .data(null)
                        .build());
    }
}
