package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamRepository extends JpaRepository<Exam, UUID> {
    @Query("SELECT EXISTS (SELECT 1 FROM Exam e WHERE e.examId = :examId AND e.createdBy.userId = :creatorId)")
    Boolean isExamCreator(@Param("creatorId") UUID creatorId, @Param("examId") UUID examId);

    List<Exam> findByCreatedByUserId(UUID teacherId);

    @Query("SELECT e FROM Exam e WHERE e.subject IN " +
            "(SELECT us.subject FROM UserSubject us WHERE us.user.userId = :studentId)")
    List<Exam> findExamsByStudentId(@Param("studentId") UUID studentId);

    @Query("SELECT e FROM Exam e WHERE e.createdBy.userId = :teacherId AND e.endDate < :now")
    List<Exam> findPastExamsByTeacher(@Param("teacherId") UUID teacherId, @Param("now") LocalDateTime now);

    @Query("SELECT e FROM Exam e WHERE e.createdBy.userId = :teacherId AND e.startDate <= :now AND e.endDate >= :now")
    List<Exam> findCurrentExamsByTeacher(@Param("teacherId") UUID teacherId, @Param("now") LocalDateTime now);
    @Query("SELECT e FROM Exam e WHERE e.createdBy.userId = :teacherId AND e.startDate > :now")
    List<Exam> findUpcomingExamsByTeacher(@Param("teacherId") UUID teacherId, @Param("now") LocalDateTime now);

    // Get average number of exams per subject for a teacher
    @Query("SELECT e.subject.subjectId, e.subject.name, COUNT(e) as examCount " +
            "FROM Exam e WHERE e.createdBy.userId = :teacherId " +
            "GROUP BY e.subject.subjectId, e.subject.name")
    List<Object[]> findExamCountBySubjectForTeacher(@Param("teacherId") UUID teacherId);
}

