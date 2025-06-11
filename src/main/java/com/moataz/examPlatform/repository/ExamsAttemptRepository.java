package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.ExamAttempts;
import com.moataz.examPlatform.model.ExamsAttemptId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamsAttemptRepository extends JpaRepository<ExamAttempts, ExamsAttemptId> {
    List<ExamAttempts> findById_UserId(UUID userId);
//    Optional<ExamAttempts> findById_ExamId(UUID ExamId);

    @Query("SELECT DISTINCT ea.id.userId FROM ExamAttempts ea " +
            "JOIN Exam e ON ea.id.ExamId = e.examId " +
            "WHERE e.createdBy.userId = :teacherId " +
            "AND e.startDate <= :now AND e.endDate >= :now " +
            "AND ea.EndDate IS NULL") // Assuming EndDate is null for ongoing attempts
    List<UUID> findCurrentStudentsInExamsByTeacher(@Param("teacherId") UUID teacherId, @Param("now") LocalDateTime now);
}
