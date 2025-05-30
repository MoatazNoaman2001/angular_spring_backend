package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.ExamAttempts;
import com.moataz.examPlatform.model.ExamsAttemptId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamsAttemptRepository extends JpaRepository<ExamAttempts, ExamsAttemptId> {
}
