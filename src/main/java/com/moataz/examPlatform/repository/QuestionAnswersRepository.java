package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.QuestionAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionAnswersRepository extends JpaRepository<QuestionAnswers, UUID> {
}
