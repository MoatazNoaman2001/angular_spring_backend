package com.moataz.examPlatform.dto;

import com.moataz.examPlatform.model.Exam;
import com.moataz.examPlatform.model.QuestionAnswers;
import com.moataz.examPlatform.model.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private UUID questionId;
    private QuestionType type;
    private String text;
    private Integer marks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
