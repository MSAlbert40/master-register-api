package com.evertix.masterregister.repository;

import com.evertix.masterregister.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByManagerId(Long managerId);
    List<User> findAllByManagerIdAndName(Long managerId, String name);
    List<User> findAllByManagerIdAndWorkAreaId(Long managerId, Long workAreaId);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findByManagerIdAndId(Long managerId, Long id);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
