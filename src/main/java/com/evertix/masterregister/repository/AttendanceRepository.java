package com.evertix.masterregister.repository;

import com.evertix.masterregister.model.Attendance;
import com.evertix.masterregister.model.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAllByStatusNameAndEmployeeManagerId(EStatus name, Long managerId);
}
