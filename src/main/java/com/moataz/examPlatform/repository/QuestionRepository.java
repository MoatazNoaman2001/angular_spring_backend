package com.moataz.examPlatform.repository;

import com.moataz.examPlatform.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
