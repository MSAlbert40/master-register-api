package com.evertix.masterregister.repository;

import com.evertix.masterregister.model.Attendance;
import com.evertix.masterregister.model.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByDate(LocalDate date);
    List<Attendance> findAllByEmployeeId(Long employeeId);
    List<Attendance> findAllByStatusNameAndEmployeeId(EStatus name, Long employeeId);
    List<Attendance> findAllByStatusNameAndEmployeeManagerId(EStatus name, Long managerId);
}
