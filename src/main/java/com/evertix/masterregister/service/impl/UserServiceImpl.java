package com.evertix.masterregister.service.impl;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.controller.constants.ResponseConstants;
import com.evertix.masterregister.model.User;
import com.evertix.masterregister.model.dto.SaveUserRequest;
import com.evertix.masterregister.repository.UserRepository;
import com.evertix.masterregister.security.request.LoginRequest;
import com.evertix.masterregister.security.request.SignUpRequest;
import com.evertix.masterregister.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseEntity<MessageResponse> getAllEmployeeByManager(Long id) {
        try {
            List<User> userList = this.userRepository.findAllByManagerId(id);
            if (userList == null || userList.isEmpty()) { return this.getNotUserContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(userList)
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
    public ResponseEntity<MessageResponse> getEmployeeByManager(Long employeeId) {
        try {
            User employee = this.userRepository.findById(employeeId).orElse(null);
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(employee)
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
    public ResponseEntity<MessageResponse> updateUser(Long id, SignUpRequest signUpRequest) {
        try {
            User saveUser = this.userRepository.findById(id).orElse(null);
            if (saveUser == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists user with ID: " + id)
                                .build());
            }
            // Update User Data
            saveUser.setUsername(signUpRequest.getUsername());
            saveUser.setPassword(encoder.encode(signUpRequest.getPassword()));
            saveUser.setEmail(signUpRequest.getEmail());
            saveUser.setName(signUpRequest.getName());
            saveUser.setLastName(signUpRequest.getLastName());
            saveUser.setDni(signUpRequest.getDni());
            saveUser.setAge(signUpRequest.getAge());
            saveUser.setGender(signUpRequest.getGender());
            saveUser.setAddress(signUpRequest.getAddress());
            saveUser.setPhone(signUpRequest.getPhone());
            saveUser.setSalary(signUpRequest.getSalary());
            // Save Update
            saveUser = userRepository.save(saveUser);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Successful update")
                            .data(this.convertToResource(saveUser))
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

    private User convertToEntity(SignUpRequest user) { return modelMapper.map(user, User.class); }

    private SaveUserRequest convertToResource(User user) {
        SaveUserRequest resource = modelMapper.map(user, SaveUserRequest.class);
        resource.setRole(user.getRole().getName().toString());
        resource.setWorkArea(user.getWorkArea().getName());
        resource.setManager(user.getManager().getLastName() + ", " + user.getManager().getName());
        return resource;
    }

    private ResponseEntity<MessageResponse> getNotUserContent() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder()
                        .code(ResponseConstants.WARNING_CODE)
                        .message(ResponseConstants.MSG_WARNING_CONS)
                        .data(null)
                        .build());
    }
}
