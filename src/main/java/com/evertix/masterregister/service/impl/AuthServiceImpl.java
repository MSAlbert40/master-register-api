package com.evertix.masterregister.service.impl;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.controller.constants.ResponseConstants;
import com.evertix.masterregister.model.Role;
import com.evertix.masterregister.model.Schedule;
import com.evertix.masterregister.model.User;
import com.evertix.masterregister.model.WorkArea;
import com.evertix.masterregister.model.enums.ERole;
import com.evertix.masterregister.repository.RoleRepository;
import com.evertix.masterregister.repository.ScheduleRepository;
import com.evertix.masterregister.repository.UserRepository;
import com.evertix.masterregister.repository.WorkAreaRepository;
import com.evertix.masterregister.security.jwt.JwtUtils;
import com.evertix.masterregister.security.request.LoginRequest;
import com.evertix.masterregister.security.request.SignUpRequest;
import com.evertix.masterregister.security.response.JwtResponse;
import com.evertix.masterregister.security.service.UserDetailsImpl;
import com.evertix.masterregister.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    WorkAreaRepository workAreaRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public ResponseEntity<MessageResponse> registerUser(SignUpRequest signUpRequest, Long scheduleId, Long workAreaId, Long managerId) {
        try {
            // Validate if Username Exists
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity.
                        status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Username is already taken")
                                .build());
            }
            // Validate if Email Exists
            else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.
                        status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Email is already taken")
                                .build());
            }

            // Validate if Schedule Exists
            Schedule schedule = this.scheduleRepository.findById(scheduleId).orElse(null);
            if (schedule == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists Schedule with ID: " + scheduleId)
                                .build());
            }

            // Validate if Work Area Exists
            WorkArea workArea = this.workAreaRepository.findById(workAreaId).orElse(null);
            if (workArea == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists Work Area with ID: " + workAreaId)
                                .build());
            }

            // Validate if Manager Exist
            User manager = null;
            if (managerId != null) { manager = this.userRepository.findById(managerId).orElse(null); }

            // Create a new User
            User createUser = new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getName(),
                    signUpRequest.getLastName(), signUpRequest.getDni(), signUpRequest.getAge(), signUpRequest.getGender(), signUpRequest.getAddress(), signUpRequest.getPhone(), signUpRequest.getSalary());
            // Choose Role User
            Role userRole;
            if (managerId != null) { userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")); }
            else { userRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found.")); }
            // Save All
            createUser.setRole(userRole);
            createUser.setSchedule(schedule);
            createUser.setWorkArea(workArea);
            createUser.setManager(manager);
            userRepository.save(createUser);
            return ResponseEntity.
                    status(HttpStatus.CREATED).
                    body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Successful Register")
                            .data(createUser)
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
    public ResponseEntity<MessageResponse> updatePassword(LoginRequest loginRequest) {
        try {
            User passwordUser = userRepository.findByUsernameOrEmail(loginRequest.getUsername(), loginRequest.getUsername()).orElse(null);
            if (passwordUser == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists user with username or email: " + loginRequest.getUsername())
                                .build());
            }
            // Change Password
            passwordUser.setPassword(encoder.encode(loginRequest.getPassword()));
            // Save Update
            userRepository.save(passwordUser);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Successful password change")
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
    public JwtResponse authenticationUser(LoginRequest loginRequest) {
        // Auth username & password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Get JWT Token
        String jwt = jwtUtils.generatedJwtToken(authentication);
        // Get User
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // Convert Role in string
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String roleOne = "";
        for (String rol: roles){ roleOne = roleOne.concat(rol); }
        // Get All Data User
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roleOne, userDetails.getName(), userDetails.getLastName(),
                userDetails.getDni(), userDetails.getAge(), userDetails.getGender(), userDetails.getAddress(), userDetails.getPhone(), userDetails.getSalary());
    }
}
