package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByExam_ExamId(UUID examId);
}
