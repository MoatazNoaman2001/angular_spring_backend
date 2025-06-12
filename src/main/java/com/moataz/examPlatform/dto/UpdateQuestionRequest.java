package com.moataz.examPlatform.dto;

import com.moataz.examPlatform.model.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuestionRequest {
    @NotNull(message = "Question ID cannot be null")
    private UUID questionId;

    @NotNull(message = "Question type cannot be null")
    private QuestionType type;

    @NotBlank(message = "Question text cannot be blank")
    private String text;

    @NotNull(message = "Marks cannot be null")
    @Min(value = 1, message = "Marks should be at least 1")
    private Integer marks;

    @NotNull(message = "Wrong answers list cannot be null")
    @NotEmpty(message = "Wrong answers list cannot be empty")
    @Valid
    private List<AnswersDto> wrongAnswer = new ArrayList<>();

    @NotNull(message = "Right answers list cannot be null")
    @NotEmpty(message = "Right answers list cannot be empty")
    @Valid
    private List<AnswersDto> rightAnswer = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
