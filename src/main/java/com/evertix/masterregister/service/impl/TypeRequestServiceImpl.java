package com.evertix.masterregister.service.impl;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.controller.constants.ResponseConstants;
import com.evertix.masterregister.model.TypeRequest;
import com.evertix.masterregister.repository.TypeRequestRepository;
import com.evertix.masterregister.service.TypeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Service
public class TypeRequestServiceImpl implements TypeRequestService {
    @Autowired
    TypeRequestRepository typeRequestRepository;

    @Override
    public ResponseEntity<MessageResponse> getAllTypeRequest() {
        try {
            List<TypeRequest> typeRequestList = this.typeRequestRepository.findAll();
            if (typeRequestList.isEmpty()) { return this.getNotTypeRequestContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(typeRequestList)
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

    private ResponseEntity<MessageResponse> getNotTypeRequestContent() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder()
                        .code(ResponseConstants.WARNING_CODE)
                        .message(ResponseConstants.MSG_WARNING_CONS)
                        .data(null)
                        .build());
    }
}
