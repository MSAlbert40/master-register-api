package com.evertix.masterregister.repository;

import com.evertix.masterregister.model.TypeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRequestRepository extends JpaRepository<TypeRequest, Long> { }
