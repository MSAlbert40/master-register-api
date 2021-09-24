package com.evertix.masterregister.service.impl;

import com.evertix.masterregister.controller.commons.MessageResponse;
import com.evertix.masterregister.controller.constants.ResponseConstants;
import com.evertix.masterregister.model.Attendance;
import com.evertix.masterregister.model.Status;
import com.evertix.masterregister.model.User;
import com.evertix.masterregister.model.dto.SaveAttendanceRequest;
import com.evertix.masterregister.model.enums.EStatus;
import com.evertix.masterregister.model.request.AttendanceRequest;
import com.evertix.masterregister.repository.AttendanceRepository;
import com.evertix.masterregister.repository.StatusRepository;
import com.evertix.masterregister.repository.UserRepository;
import com.evertix.masterregister.service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Override
    public ResponseEntity<MessageResponse> getAllAttendances(String status, Long managerId) {
        try {
            // Identify Status
            EStatus statusNow;
            if (status == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Sorry, Status not found")
                                .build());
            } else {
                switch (status) {
                    case "STATUS_ABSENT": statusNow = EStatus.STATUS_ABSENT; break;
                    case "STATUS_LATE": statusNow = EStatus.STATUS_LATE; break;
                    case "STATUS_ATTENDANCE": statusNow = EStatus.STATUS_ATTENDANCE; break;
                    default: throw new RuntimeException("Sorry, Type Wallet is wrong.");
                };
            }

            List<Attendance> attendanceList = this.attendanceRepository.findAllByStatusNameAndEmployeeManagerId(statusNow, managerId);
            if(attendanceList == null || attendanceList.isEmpty()) { return this.getNotAttendanceContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(attendanceList)
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
    public ResponseEntity<MessageResponse> getAllAttendancesByDate(LocalDate date) {
        try {
            List<Attendance> attendanceList = this.attendanceRepository.findAllByDate(date);
            if(attendanceList == null || attendanceList.isEmpty()) { return this.getNotAttendanceContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(attendanceList)
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
    public ResponseEntity<MessageResponse> getAllAttendancesByEmployee(String status, Long employeeId) {
        try {
            // Identify Status
            EStatus statusNow;
            if (status == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Sorry, Status not found")
                                .build());
            } else {
                switch (status) {
                    case "STATUS_ABSENT": statusNow = EStatus.STATUS_ABSENT; break;
                    case "STATUS_LATE": statusNow = EStatus.STATUS_LATE; break;
                    case "STATUS_ATTENDANCE": statusNow = EStatus.STATUS_ATTENDANCE; break;
                    default: throw new RuntimeException("Sorry, Type Wallet is wrong.");
                };
            }

            List<Attendance> attendanceList = this.attendanceRepository.findAllByStatusNameAndEmployeeId(statusNow, employeeId);
            if(attendanceList == null || attendanceList.isEmpty()) { return this.getNotAttendanceContent(); }
            MessageResponse response = MessageResponse.builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.MSG_SUCCESS_CONS)
                    .data(attendanceList)
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
    public ResponseEntity<MessageResponse> markAttend(Long employeeId, AttendanceRequest attendance) {
        try {
            // Valid if Employee Exists
            User employee = this.userRepository.findById(employeeId).orElse(null);
            if (employee == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(MessageResponse.builder()
                                .code(ResponseConstants.ERROR_CODE)
                                .message("Don't exists employee with ID: " + employeeId)
                                .build());
            }

            Attendance saveAttendance;
            // Separate Date with Time
            LocalDateTime nowDateTime = attendance.getDateTime();
            LocalDate nowDate = LocalDate.of(nowDateTime.getYear(), nowDateTime.getMonth(), nowDateTime.getDayOfMonth());
            LocalTime nowTime = LocalTime.of(nowDateTime.getHour(), nowDateTime.getMinute(), nowDateTime.getSecond());

            // Validate Attendance
            long duration = Duration.between(employee.getSchedule().getTimeIn(), nowTime).toMinutes();
            long work = Duration.between(employee.getSchedule().getTimeIn(), employee.getSchedule().getTimeOut()).toMinutes();

            // List of Employee
            List<Attendance> employeeList = this.attendanceRepository.findAllByEmployeeId(employeeId);

            // Count All Attendance
            Status status = new Status();
            int quantity = 0;
            int lat = 0; int abs = 0; int att = 0;

            for (Attendance all : employeeList) {
                lat = all.getLate() + lat;
                abs = all.getAbsent() + abs;
                att = all.getAttendance() + abs;
            }

            // Identify Status
            if (duration <= 30) {
                status = this.statusRepository.findByName(EStatus.STATUS_ATTENDANCE).orElse(null);
                att = att + 1;
                // numHours = (int) work;
            }
            else if (duration < work) {
                status = this.statusRepository.findByName(EStatus.STATUS_LATE).orElse(null);
                lat = lat + 1;
                // numHours = (int) duration - 30;
            }
            else if (duration > work) {
                status = this.statusRepository.findByName(EStatus.STATUS_ABSENT).orElse(null);
                abs = abs + 1;
                // numHours = 0;
            }

            // Save Attendance
            saveAttendance = new Attendance(nowDate, nowTime, att, lat, abs, quantity);
            saveAttendance.setStatus(status);
            saveAttendance.setEmployee(employee);
            attendanceRepository.save(saveAttendance);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(MessageResponse.builder()
                            .code(ResponseConstants.SUCCESS_CODE)
                            .message("Successful creation request")
                            .data(this.convertToResource(saveAttendance))
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

    private Attendance convertToEntity(AttendanceRequest attendance) { return modelMapper.map(attendance, Attendance.class); }

    private SaveAttendanceRequest convertToResource(Attendance attendance) {
        SaveAttendanceRequest resource = modelMapper.map(attendance, SaveAttendanceRequest.class);
        resource.setStatus(attendance.getStatus().getName().toString());
        resource.setManager(attendance.getEmployee().getManager().getLastName() + ", " + attendance.getEmployee().getManager().getName());
        return resource;
    }

    private ResponseEntity<MessageResponse> getNotAttendanceContent() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageResponse.builder()
                        .code(ResponseConstants.WARNING_CODE)
                        .message(ResponseConstants.MSG_WARNING_CONS)
                        .data(null)
                        .build());
    }
}
