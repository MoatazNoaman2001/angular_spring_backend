package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.ExamAttempts;
import com.moataz.examPlatform.model.ExamsAttemptId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExamsAttemptRepository extends JpaRepository<ExamAttempts, ExamsAttemptId> {
    List<ExamAttempts> findById_UserId(UUID userId);
}
