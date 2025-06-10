package com.moataz.examPlatform.repository;


import com.moataz.examPlatform.model.Role;
import com.moataz.examPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    List<User> findByUserType(Role userType);
}