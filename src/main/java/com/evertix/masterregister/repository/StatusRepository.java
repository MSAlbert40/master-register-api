package com.evertix.masterregister.repository;

import com.evertix.masterregister.model.Status;
import com.evertix.masterregister.model.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByName(EStatus name);
}
