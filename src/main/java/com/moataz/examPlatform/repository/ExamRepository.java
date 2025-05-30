package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamRepository extends JpaRepository<Exam, UUID> {
    List<Exam> findByCreatedByUserId(UUID teacherId);
    @Query("SELECT e FROM Exam e WHERE e.subject IN " +
            "(SELECT us.subject FROM UserSubject us WHERE us.user.userId = :studentId)")
    List<Exam> findExamsByStudentId(@Param("studentId") UUID studentId);
}
