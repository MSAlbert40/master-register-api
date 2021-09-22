package com.evertix.masterregister.repository;

import com.evertix.masterregister.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEmployeeId(Long employeeId);
    List<Request> findAllByEmployeeManagerId(Long managerId);
    List<Request> findAllByEmployeeIdAndTypeRequestId(Long employeeId, Long typeRequestId);
    List<Request> findAllByEmployeeManagerIdAndTypeRequestId(Long managerId, Long typeRequestId);
}
