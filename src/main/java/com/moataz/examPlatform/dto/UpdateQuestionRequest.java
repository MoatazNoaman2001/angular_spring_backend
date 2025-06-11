package com.moataz.examPlatform.dto;

import com.moataz.examPlatform.model.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuestionRequest {
    private UUID questionId;
    private QuestionType type;
    private String text;
    private Integer marks;
    private Boolean isRight;
    private List<AnswersDto> wrongAnswer;
    private List<AnswersDto> rightAnswer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
