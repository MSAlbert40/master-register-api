package com.evertix.masterregister.service;

import com.evertix.masterregister.controller.commons.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface WorkAreaService {
    ResponseEntity<MessageResponse> getAllWorkArea();
}
