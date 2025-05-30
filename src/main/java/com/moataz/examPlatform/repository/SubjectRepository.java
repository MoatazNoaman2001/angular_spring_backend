package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject , UUID> {
}
